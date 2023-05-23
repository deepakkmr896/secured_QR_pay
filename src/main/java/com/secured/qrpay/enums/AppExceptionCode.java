package com.secured.qrpay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AppExceptionCode {
    NO_PG_AVAILABLE("No Payment Gateway Available!"),
    UNABLE_TO_SAVE_PAYEE("Not able to save the payee details!"),
    UNABLE_TO_SAVE_CUSTOMER("Not able to save the customer details!"),
    NOT_VALID_REQUEST("Request is not valid!"),
    NO_PAYEE_FOUND,
    NO_CUSTOMER_FOUND,
    PAYMENT_FAILED,
    NO_ACTIVE_CARDS_AVAILABLE;
    String errorMsg;
}
