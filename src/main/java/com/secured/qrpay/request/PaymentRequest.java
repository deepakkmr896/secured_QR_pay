package com.secured.qrpay.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secured.qrpay.enums.Currency;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class PaymentRequest {
    private Integer transactionAmount;
    private Currency currency;
    private Long customerId;
    private Long payeeId;
    @ToString.Exclude
    private String cardToken;
    @JsonIgnore
    private String source;
    @JsonIgnore
    private String gatewayCustomerIdentifier;
}