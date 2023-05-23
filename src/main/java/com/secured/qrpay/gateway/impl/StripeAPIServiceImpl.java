package com.secured.qrpay.gateway.impl;

import com.secured.qrpay.gateway.Property;
import com.secured.qrpay.gateway.StripeAPIService;
import com.secured.qrpay.gateway.response.StripeAllCardsResponse;
import com.secured.qrpay.gateway.response.StripeAllCustomersResponse;
import com.secured.qrpay.http.HttpManager;
import com.secured.qrpay.http.RequestParam;
import com.secured.qrpay.request.CustomerRequest;
import com.secured.qrpay.request.PaymentRequest;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StripeAPIServiceImpl implements StripeAPIService {
    @Value("${app.config.stripe.secret-key}")
    private String secretKey;

    @Autowired
    private HttpManager httpManager;

    @Override
    public Customer createCustomer(CustomerRequest customerRequest) {
        String url = Property.STRIPE_BASE_URL + Property.STRIPE_CREATE_CUSTOMER_URL;
        Map<RequestParam, String> paramMap = new HashMap<>();
        paramMap.put(RequestParam.EMAIL, customerRequest.getEmail());
        paramMap.put(RequestParam.NAME, customerRequest.getCustomerName());
        paramMap.put(RequestParam.PHONE, customerRequest.getPhoneNumber());
        paramMap.put(RequestParam.AUTHORIZATION, "Bearer " + secretKey);
        Customer response = httpManager.post(url, paramMap, 5, Customer.class);
        return response;
    }

    @Override
    public List<Customer> getAllCustomer(String email) {
        String url = Property.STRIPE_BASE_URL + Property.STRIPE_GET_ALL_CUSTOMERS_URL;
        Map<RequestParam, String> paramMap = new HashMap<>();
        paramMap.put(RequestParam.EMAIL, email);
        paramMap.put(RequestParam.EXPAND, RequestParam.DATA_DEFAULT_SOURCE.getName());
        paramMap.put(RequestParam.AUTHORIZATION, "Bearer " + secretKey);
        StripeAllCustomersResponse response = httpManager.get(url, paramMap, 5, StripeAllCustomersResponse.class);
        return response.getData();
    }

    @Override
    public Charge createCharge(PaymentRequest paymentRequest) {
        String url = Property.STRIPE_BASE_URL + Property.STRIPE_CREATE_CHARGE_URL;
        Map<RequestParam, String> paramMap = new HashMap<>();
        paramMap.put(RequestParam.CUSTOMER_ID, paymentRequest.getGatewayCustomerIdentifier());
        paramMap.put(RequestParam.AMOUNT, paymentRequest.getTransactionAmount().toString());
        paramMap.put(RequestParam.CURRENCY, paymentRequest.getCurrency().getCurrencyCode());
        paramMap.put(RequestParam.SOURCE, paymentRequest.getSource());
        paramMap.put(RequestParam.AUTHORIZATION, "Bearer " + secretKey);
        Charge response = httpManager.post(url, paramMap, 5, Charge.class);
        return response;
    }

    @Override
    public Charge getCharge(String chargeId) {
        String url = Property.STRIPE_BASE_URL + Property.STRIPE_GET_CHARGE_URL;
        url.replace("{charge_id}", chargeId);
        Map<RequestParam, String> paramMap = new HashMap<>();
        paramMap.put(RequestParam.AUTHORIZATION, "Bearer " + secretKey);
        Charge response = httpManager.get(url, paramMap, 5, Charge.class);
        return response;
    }

    @Override
    public Card createCard(String source, String customerId) {
        String url = Property.STRIPE_BASE_URL + Property.STRIPE_CREATE_CARD_URL;
        url = url.replace("{customer_id}", customerId);
        Map<RequestParam, String> paramMap = new HashMap<>();
        paramMap.put(RequestParam.SOURCE, source);
        paramMap.put(RequestParam.EXPAND, RequestParam.CUSTOMER_ID.getName());
        paramMap.put(RequestParam.AUTHORIZATION, "Bearer " + secretKey);
        Card response = httpManager.post(url, paramMap, 5, Card.class);
        return response;
    }

    @Override
    public List<Card> getAllCards(String customerId) {
        String url = Property.STRIPE_BASE_URL + Property.STRIPE_CREATE_CARD_URL;
        url = url.replace("{customer_id}", customerId);
        Map<RequestParam, String> paramMap = new HashMap<>();
        paramMap.put(RequestParam.EXPAND, RequestParam.CUSTOMER_ID.getName());
        paramMap.put(RequestParam.AUTHORIZATION, "Bearer " + secretKey);
        StripeAllCardsResponse response = httpManager.post(url, paramMap, 5, StripeAllCardsResponse.class);
        return response.getData();
    }
}
