package com.cts.grantserve.controller;

import com.cts.grantserve.dto.PaymentDto;
import com.cts.grantserve.entity.Payment;
import com.cts.grantserve.enums.PaymentMethod;
import com.cts.grantserve.service.IPaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @PostMapping("/process")
    public String process(@Valid @RequestBody PaymentDto dto) {
        Payment payment = paymentService.processPayment(dto);
        return "Payment processed";
    }

    @GetMapping("/disbursement/{disbursementId}")
    public ResponseEntity<Payment> getByDisbursement(@PathVariable Long disbursementId) {
        return ResponseEntity.ok(paymentService.getPaymentByDisbursement(disbursementId));
    }

    @GetMapping("/method/{method}")
    public ResponseEntity<List<Payment>> getByMethod(@PathVariable PaymentMethod method) {
        return ResponseEntity.ok(paymentService.getPaymentsByMethod(method));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAll() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }
}