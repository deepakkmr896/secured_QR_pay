package com.secured.qrpay.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class PaymentResponse extends BaseResponse {
    String transactionId;
}
