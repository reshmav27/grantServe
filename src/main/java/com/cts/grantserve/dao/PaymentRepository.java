package com.cts.grantserve.dao;

import com.cts.grantserve.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Monitor actual payment linked to a disbursement ID [cite: 64, 65]
    Optional<Payment> findByDisbursement_DisbursementID(Long disbursementID);

    // Track payments by method (Bank/Wallet) [cite: 67]
    List<Payment> findByMethod(String method);
}