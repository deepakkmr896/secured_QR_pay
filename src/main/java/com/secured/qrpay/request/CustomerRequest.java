package com.secured.qrpay.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {
    private String customerName;
    private String email;
    private String phoneNumber;
}
