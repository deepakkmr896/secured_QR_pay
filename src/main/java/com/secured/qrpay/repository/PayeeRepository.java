package com.secured.qrpay.repository;

import com.secured.qrpay.entity.Payee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayeeRepository extends JpaRepository<Payee, Long> {
}
