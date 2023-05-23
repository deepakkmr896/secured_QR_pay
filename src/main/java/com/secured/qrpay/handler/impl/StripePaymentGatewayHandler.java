package com.secured.qrpay.handler.impl;

import com.secured.qrpay.entity.Customer;
import com.secured.qrpay.entity.Payee;
import com.secured.qrpay.entity.Transaction;
import com.secured.qrpay.enums.AppExceptionCode;
import com.secured.qrpay.enums.PaymentGateway;
import com.secured.qrpay.enums.PaymentStatus;
import com.secured.qrpay.exception.AppException;
import com.secured.qrpay.factory.EncryptHandlerFactory;
import com.secured.qrpay.gateway.StripeAPIService;
import com.secured.qrpay.handler.EncryptionHandler;
import com.secured.qrpay.handler.PaymentGatewayHandler;
import com.secured.qrpay.repository.CustomerRepository;
import com.secured.qrpay.repository.PayeeRepository;
import com.secured.qrpay.repository.TransactionRepository;
import com.secured.qrpay.request.CustomerRequest;
import com.secured.qrpay.request.PaymentRequest;
import com.secured.qrpay.response.PaymentResponse;
import com.secured.qrpay.util.BasicUtil;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class StripePaymentGatewayHandler implements PaymentGatewayHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(StripePaymentGatewayHandler.class);

    @Autowired
    private PayeeRepository payeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StripeAPIService stripeAPIService;

    @Autowired
    private EncryptHandlerFactory encryptHandlerFactory;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public PaymentResponse initiatePayment(PaymentRequest paymentRequest) throws Exception {
        Optional<Payee> payeeOptional = payeeRepository.findById(paymentRequest.getPayeeId());
        if (!payeeOptional.isPresent()) {
            throw new AppException(AppExceptionCode.NO_PAYEE_FOUND.name());
        }
        Optional<Customer> customerOptional = customerRepository.findById((Long) paymentRequest.getCustomerId());
        if (!customerOptional.isPresent()) {
            throw new AppException(AppExceptionCode.NO_CUSTOMER_FOUND.name());
        }
        Customer customer = customerOptional.get();
        EncryptionHandler encryptionHandler = encryptHandlerFactory.getHandler("AES");
        customer.setDecryptedEmail(encryptionHandler.decrypt(customer.getEmail()));
        customer.setDecryptedPhoneNumber(encryptionHandler.decrypt(customer.getPhoneNumber()));
        List<com.stripe.model.Customer> stripeCustomers = stripeAPIService.getAllCustomer(customer.getDecryptedEmail());
        com.stripe.model.Customer stripeCustomer = null;
        if (BasicUtil.isNotEmpty(stripeCustomers)) {
            Optional<com.stripe.model.Customer> stripeCustomerOptional = stripeCustomers.stream()
                    .filter(cus -> cus.getEmail().equalsIgnoreCase(customer.getDecryptedEmail())
                            && cus.getPhone().equalsIgnoreCase(customer.getDecryptedPhoneNumber())
                            && cus.getName().equals(customer.getName())).findFirst();
            if (stripeCustomerOptional.isPresent()) {
                stripeCustomer = stripeCustomerOptional.get();
            }
        }
        if (BasicUtil.isNull(stripeCustomer)) {
            CustomerRequest customerRequest = new CustomerRequest();
            customerRequest.setCustomerName(customer.getName());
            customerRequest.setEmail(customer.getDecryptedEmail());
            customerRequest.setPhoneNumber(customer.getDecryptedPhoneNumber());
            stripeCustomer = stripeAPIService.createCustomer(customerRequest);
            if (BasicUtil.isNull(stripeCustomer) || BasicUtil.isEmpty(stripeCustomer.getId())) {
                throw new AppException(AppExceptionCode.NO_CUSTOMER_FOUND.getErrorMsg());
            }
        }
        paymentRequest.setGatewayCustomerIdentifier(stripeCustomer.getId());
        if (BasicUtil.isNotNull(paymentRequest.getCardToken())) {
            try {
                Card card = stripeAPIService.createCard(paymentRequest.getCardToken(), paymentRequest.getGatewayCustomerIdentifier());
                paymentRequest.setSource(card.getId());
            } catch (Exception e) {
                LOGGER.error("Error occurred while saving card having source : {}, error : {}", paymentRequest.getSource(), e.getMessage());
            }
        } else {
            if (BasicUtil.isEmpty(stripeCustomer.getDefaultSource())) {
                throw new AppException(AppExceptionCode.NO_ACTIVE_CARDS_AVAILABLE.name());
            }
            paymentRequest.setSource(stripeCustomer.getDefaultSource());
        }
        Charge charge = stripeAPIService.createCharge(paymentRequest);
        if (BasicUtil.isNotNull(charge) && BasicUtil.isNotEmpty(charge.getId())) {
            PaymentResponse response = PaymentResponse.builder().message("PAYMENT_SUCCESS").transactionId(charge.getId()).build();
            try {
                Transaction transaction = new Transaction();
                transaction.setId(charge.getId());
                transaction.setCurrency(paymentRequest.getCurrency());
                transaction.setTransactionAmount(paymentRequest.getTransactionAmount());
                transaction.setPaymentStatus(PaymentStatus.SUCCESS);
                transaction.setCreatedTime(LocalDateTime.now());
                transaction.setPayee(payeeOptional.get());
                transaction.setCustomer(customer);
                transactionRepository.save(transaction);
            } catch (Exception e) {
                LOGGER.info("Unable to save transaction details into DB, for id : {}!", charge.getId());
            }

            return response;
        } else {
            throw new AppException(AppExceptionCode.PAYMENT_FAILED.name());
        }
    }

    @Override
    public PaymentGateway getPaymentGateway() {
        return PaymentGateway.STRIPE;
    }
}
