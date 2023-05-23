package com.secured.qrpay.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
public class Payee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "is_authenticated")
    private boolean isAuthenticated;

    @Column(name = "next_validated_time")
    private LocalDateTime nextValidatedTime;

    @Column(name = "validation_time_interval")
    private Short validationTimeIntervalInHour;

    @OneToMany(mappedBy = "payee")
    private List<Transaction> transactions;
}
