package com.secured.qrpay.service;

import com.secured.qrpay.request.PaymentRequest;
import com.secured.qrpay.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse initiatePayment(PaymentRequest paymentRequest);
}
