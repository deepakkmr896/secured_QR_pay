package com.secured.qrpay.service.impl;

import com.secured.qrpay.enums.AppExceptionCode;
import com.secured.qrpay.exception.AppException;
import com.secured.qrpay.factory.PaymentGatewayHandlerFactory;
import com.secured.qrpay.handler.PaymentGatewayHandler;
import com.secured.qrpay.handler.PaymentGatewaySelectorHandler;
import com.secured.qrpay.request.PaymentRequest;
import com.secured.qrpay.response.PaymentResponse;
import com.secured.qrpay.service.PaymentService;
import com.secured.qrpay.util.BasicUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentGatewayHandlerFactory paymentGatewayHandlerFactory;

    @Autowired
    private PaymentGatewaySelectorHandler paymentGatewaySelectorHandler;

    @Override
    @SneakyThrows
    public PaymentResponse initiatePayment(PaymentRequest paymentRequest) {
        PaymentGatewayHandler paymentGatewayHandler = paymentGatewayHandlerFactory.getHandler(paymentGatewaySelectorHandler.selectPaymentGateway());
        if (BasicUtil.isNull(paymentGatewayHandler)) {
            throw new AppException(AppExceptionCode.NO_PG_AVAILABLE.getErrorMsg());
        }
        if (!validRequest(paymentRequest)) {
            throw new AppException(AppExceptionCode.NOT_VALID_REQUEST.getErrorMsg());
        }
        return paymentGatewayHandler.initiatePayment(paymentRequest);
    }

    private boolean validRequest(PaymentRequest paymentRequest) {
        return BasicUtil.isNotNull(paymentRequest.getCustomerId())
                && BasicUtil.isNotNull(paymentRequest.getPayeeId())
                && BasicUtil.isNotNull(paymentRequest.getTransactionAmount());

    }
}
