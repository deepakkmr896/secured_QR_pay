package com.secured.qrpay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Currency {
    INR("inr", "Indian Currency"),
    USD("usd", "US Currency");

    private String currencyCode;
    private String name;
}
