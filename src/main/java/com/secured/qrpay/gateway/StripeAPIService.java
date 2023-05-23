package com.secured.qrpay.gateway;

import com.secured.qrpay.request.CustomerRequest;
import com.secured.qrpay.request.PaymentRequest;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;

import java.util.List;


public interface StripeAPIService {
    Customer createCustomer(CustomerRequest customerRequest);
    List<Customer> getAllCustomer(String email);
    Charge createCharge(PaymentRequest paymentRequest);

    Charge getCharge(String chargeId);

    Card createCard(String source, String customerId);
    List<Card> getAllCards(String customerId);
}
