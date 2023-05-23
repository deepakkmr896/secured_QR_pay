package com.secured.qrpay.factory;

import com.secured.qrpay.enums.AppExceptionCode;
import com.secured.qrpay.enums.PaymentGateway;
import com.secured.qrpay.exception.AppException;
import com.secured.qrpay.handler.PaymentGatewayHandler;
import com.secured.qrpay.util.BasicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PaymentGatewayHandlerFactory {
    @Autowired
    private Set<PaymentGatewayHandler> paymentGatewayHandlers;

    public PaymentGatewayHandler getHandler(PaymentGateway paymentGateway) throws AppException {
        if (BasicUtil.isNull(paymentGateway)) {
            throw new AppException(AppExceptionCode.NO_PG_AVAILABLE.getErrorMsg());
        }
        return paymentGatewayHandlers.stream()
                .filter(eh -> eh.getPaymentGateway().equals(paymentGateway))
                .findFirst().orElse(null);
    }
}
