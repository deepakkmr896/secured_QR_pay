package com.secured.qrpay.entity;

import com.secured.qrpay.enums.Currency;
import com.secured.qrpay.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    private String id;

    @Column(name = "transact_amount")
    private Integer transactionAmount;

    @Column
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "modified_time")
    private LocalDateTime modifiedTime;

    @ManyToOne
    @JoinColumn(name = "payee_id")
    private Payee payee;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
