package com.secured.qrpay.handler;

import com.secured.qrpay.enums.PaymentGateway;
import com.secured.qrpay.request.PaymentRequest;
import com.secured.qrpay.response.PaymentResponse;

public interface PaymentGatewayHandler {
    PaymentResponse initiatePayment(PaymentRequest paymentRequest) throws Exception;
    PaymentGateway getPaymentGateway();
}
