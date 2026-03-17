package com.cts.grantserve.service;

import com.cts.grantserve.DTO.PaymentDto;
import com.cts.grantserve.entity.Payment;
import com.cts.grantserve.enums.PaymentMethod;
import java.util.List;

public interface IPaymentService {
    Payment processPayment(PaymentDto dto);
    List<Payment> getPaymentsByMethod(PaymentMethod method);
    Payment getPaymentByDisbursement(Long disbursementID);
    List<Payment> getAllPayments();
    void deletePayment(Long paymentID);
}