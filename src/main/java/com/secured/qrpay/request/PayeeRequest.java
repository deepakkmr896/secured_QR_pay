package com.secured.qrpay.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayeeRequest {
    private String payeeName;
    private String phoneNumber;
    private String email;
    private Short setValidationTimeIntervalInHour;
}
