package com.secured.qrpay.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.secured.qrpay.enums.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseResponse {
    Status status;
    String message;
}
