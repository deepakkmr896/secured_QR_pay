package com.secured.qrpay.handler;

import com.secured.qrpay.enums.PaymentGateway;

public interface PaymentGatewaySelectorHandler {
    PaymentGateway selectPaymentGateway();
}
