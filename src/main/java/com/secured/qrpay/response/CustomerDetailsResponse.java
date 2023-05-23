package com.secured.qrpay.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class CustomerDetailsResponse extends BaseResponse {
    private long id;
    private String name;
    private String email;
    private String phoneNumber;
}
