package com.secured.qrpay.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestParam {
    EMAIL("email", true, false),
    NAME("name", true, false),
    PHONE("phone", true, false),
    AMOUNT("amount", true, false),
    CURRENCY("currency", true, false),
    SOURCE("source", true, false),
    CUSTOMER_ID("customer", true, false),
    EXPAND("expand[]", true, false),
    DATA_DEFAULT_SOURCE("data.default_source", true, false),
    AUTHORIZATION("Authorization", false, true);
    String name;
    boolean isRequestBody;
    boolean isHeader;
}
