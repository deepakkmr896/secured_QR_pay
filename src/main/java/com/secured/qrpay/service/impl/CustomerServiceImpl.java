package com.secured.qrpay.service.impl;

import com.secured.qrpay.entity.Customer;
import com.secured.qrpay.enums.AppExceptionCode;
import com.secured.qrpay.enums.Status;
import com.secured.qrpay.exception.AppException;
import com.secured.qrpay.factory.EncryptHandlerFactory;
import com.secured.qrpay.handler.EncryptionHandler;
import com.secured.qrpay.repository.CustomerRepository;
import com.secured.qrpay.request.CustomerRequest;
import com.secured.qrpay.response.BaseResponse;
import com.secured.qrpay.response.CustomerDetailsResponse;
import com.secured.qrpay.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private EncryptHandlerFactory encryptHandlerFactory;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public BaseResponse saveCustomerDetails(CustomerRequest customerRequest) throws AppException {
        try {
            LOGGER.info("Getting to save the customer details!");
            Customer customer = new Customer();
            customer.setName(customerRequest.getCustomerName());
            EncryptionHandler encryptionHandler = encryptHandlerFactory.getHandler("AES");
            customer.setPhoneNumber(encryptionHandler.encrypt(customerRequest.getPhoneNumber()));
            customer.setEmail(encryptionHandler.encrypt(customerRequest.getEmail()));
            customer = customerRepository.save(customer);
            return BaseResponse.builder()
                    .message("Saved Customer Successfully with id " + customer.getId())
                    .status(Status.SUCCESS)
                    .build();

        } catch (Exception e) {
            throw new AppException(AppExceptionCode.UNABLE_TO_SAVE_CUSTOMER.getErrorMsg());
        }
    }

    @Override
    public BaseResponse getCustomerDetailsById(Long id) throws Exception {
        try {
            LOGGER.info("Getting to fetch the customer details for id {}", id);
            Optional<Customer> customerDetailsOptional = customerRepository.findById(id);
            if (customerDetailsOptional.isPresent()) {
                Customer customerDetails = customerDetailsOptional.get();
                EncryptionHandler encryptionHandler = encryptHandlerFactory.getHandler("AES");
                return CustomerDetailsResponse.builder()
                        .id(customerDetails.getId())
                        .phoneNumber(encryptionHandler.decrypt(customerDetails.getPhoneNumber()))
                        .name(customerDetails.getName())
                        .email(encryptionHandler.decrypt(customerDetails.getEmail()))
                        .status(Status.SUCCESS)
                        .build();
            }
        } catch (Exception e) {
            throw new AppException("Not able to fetch the customer details for id " + id);
        }
        return null;
    }
}
