package com.secured.qrpay.gateway;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Property {
    public static final String STRIPE_BASE_URL = "https://api.stripe.com/v1";
    public static final String STRIPE_CREATE_CUSTOMER_URL = "/customers";
    public static final String STRIPE_GET_ALL_CUSTOMERS_URL = "/customers";
    public static final String STRIPE_CREATE_CHARGE_URL = "/charges";
    public static final String STRIPE_GET_CHARGE_URL = "/charges/{charge_id}";
    public static final String STRIPE_CREATE_CARD_URL = "/customers/{customer_id}/sources";
}
