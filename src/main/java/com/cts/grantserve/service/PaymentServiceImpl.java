package com.cts.grantserve.service;

import com.cts.grantserve.dto.PaymentDto;
import com.cts.grantserve.entity.Disbursement;
import com.cts.grantserve.entity.Payment;
import com.cts.grantserve.enums.PaymentMethod;
import com.cts.grantserve.exception.DisbursementException;
import com.cts.grantserve.exception.PaymentException;
import com.cts.grantserve.repository.DisbursementRepository;
import com.cts.grantserve.repository.PaymentRepository;
import com.cts.grantserve.util.ClassUtilSeparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private DisbursementRepository disbursementRepo;

    @Override
    @Transactional
    public Payment processPayment(PaymentDto dto) {
        if (dto.method() != PaymentMethod.BANK && dto.method() != PaymentMethod.WALLET) {
            throw new DisbursementException("Invalid method! Use BANK or WALLET", HttpStatus.NOT_ACCEPTABLE);
        }
        // Check if Disbursement exists
        Disbursement disbursement = disbursementRepo.findById(dto.disbursementID())
                .orElseThrow(() -> new PaymentException("Disbursement not found with ID: " + dto.disbursementID(), HttpStatus.NOT_FOUND));

        // Check if payment already exists for this disbursement
        paymentRepo.findByDisbursement_DisbursementID(dto.disbursementID())
                .ifPresent(p -> {
                    throw new PaymentException("Payment already processed for this disbursement", HttpStatus.CONFLICT);
                });

        Payment payment = ClassUtilSeparator.PaymentUtil(dto);
        payment.setDisbursement(disbursement);

        // Update Disbursement Status
        disbursement.setStatus("COMPLETED");
        disbursementRepo.save(disbursement);

        return paymentRepo.save(payment);
    }

    @Override
    public List<Payment> getPaymentsByMethod(PaymentMethod method) {
        return paymentRepo.findByMethod(method);
    }

    @Override
    public Payment getPaymentByDisbursement(Long disbursementID) {
        return paymentRepo.findByDisbursement_DisbursementID(disbursementID)
                .orElseThrow(() -> new PaymentException("No payment found for Disbursement ID: " + disbursementID, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepo.findAll();
    }

    @Override
    @Transactional
    public void deletePayment(Long paymentID) {
        if (!paymentRepo.existsById(paymentID)) {
            throw new PaymentException("Payment ID not found", HttpStatus.NOT_FOUND);
        }
        paymentRepo.deleteById(paymentID);
    }
}