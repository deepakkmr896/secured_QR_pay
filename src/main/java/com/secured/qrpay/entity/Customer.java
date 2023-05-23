package com.secured.qrpay.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column(name = "phone")
    private String phoneNumber;

    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions;

    @Transient
    private String decryptedEmail;

    @Transient
    private String decryptedPhoneNumber;
}
