package com.secured.qrpay.gateway.response;

import com.stripe.model.Card;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class StripeAllCardsResponse implements Serializable {
    private static final long serialVersionUID = -196578585849121251L;
    String url;
    List<Card> data;
    String object;
    Boolean has_more;
}
