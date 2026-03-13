package com.cts.grantserve.Repository;

import com.cts.grantserve.entity.Payment;
import com.cts.grantserve.enums.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByDisbursement_DisbursementID(Long disbursementID);

    List<Payment> findByMethod(PaymentMethod method);
}