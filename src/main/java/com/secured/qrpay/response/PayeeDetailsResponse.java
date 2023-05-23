package com.secured.qrpay.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
@Setter
public class PayeeDetailsResponse extends BaseResponse {
    private long id;
    private String name;
    private String email;
    private String phoneNumber;
    private boolean isAuthenticated;
    private LocalDateTime nextValidatedTime;
}
