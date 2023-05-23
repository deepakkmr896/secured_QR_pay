package com.secured.qrpay.handler.impl;

import com.secured.qrpay.enums.PaymentGateway;
import com.secured.qrpay.handler.PaymentGatewaySelectorHandler;
import org.springframework.stereotype.Component;


@Component
public class PaymentGatewaySelectorHandlerImpl implements PaymentGatewaySelectorHandler {
    @Override
    public PaymentGateway selectPaymentGateway() {
        int paymentGatewayIndex = (int) (Math.random() * 100) % PaymentGateway.values().length;
        return PaymentGateway.values()[paymentGatewayIndex];
    }
}
