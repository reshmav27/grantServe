package com.cts.grantserve.service;
import com.cts.grantserve.repository.DisbursementRepository;
import com.cts.grantserve.repository.PaymentRepository;
import com.cts.grantserve.entity.Disbursement;
import com.cts.grantserve.entity.Payment;
import com.cts.grantserve.enums.PaymentMethod;
import com.cts.grantserve.exception.PaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private DisbursementRepository disbursementRepo;

    @Transactional
    public Payment processPayment(Long disbursementID, PaymentMethod method) {

        Disbursement disbursement = disbursementRepo.findById(disbursementID)
                .orElseThrow(() -> new PaymentException("Disbursement not found with ID: " + disbursementID));

        Payment payment = new Payment();
        payment.setDisbursement(disbursement);
        payment.setMethod(method);
        payment.setStatus("SUCCESS");
        payment.setDate(LocalDate.now());

        disbursement.setStatus("COMPLETED");
        disbursementRepo.save(disbursement);
        return paymentRepo.save(payment);
    }

    public List<Payment> getPaymentsByMethod(PaymentMethod method) {
        return paymentRepo.findByMethod(method);
    }

    public Payment getPaymentByDisbursement(Long disbursementID) {
        return paymentRepo.findByDisbursement_DisbursementID(disbursementID)
                .orElseThrow(() -> new PaymentException("No payment found for Disbursement ID: " + disbursementID));
    }

    public List<Payment> getAllPayments() {
        return paymentRepo.findAll();
    }

    @Transactional
    public void deletePayment(Long paymentID) {
        paymentRepo.deleteById(paymentID);
    }
}