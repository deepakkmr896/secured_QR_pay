package com.secured.qrpay.gateway.response;

import com.stripe.model.Customer;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class StripeAllCustomersResponse implements Serializable {
    private static final long serialVersionUID = -196578585849121210L;
    String url;
    List<Customer> data;
    String object;
    Boolean has_more;
}
