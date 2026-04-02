package com.cts.grantserve.service;

import com.cts.grantserve.dto.PaymentDto;
import com.cts.grantserve.entity.Disbursement;
import com.cts.grantserve.entity.Payment;
import com.cts.grantserve.enums.PaymentMethod;
import com.cts.grantserve.exception.PaymentException;
import com.cts.grantserve.repository.DisbursementRepository;
import com.cts.grantserve.repository.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepo;

    @Mock
    private DisbursementRepository disbursementRepo;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    // --- Tests for processPayment ---

    @Test
    @DisplayName("processPayment: Should successfully process payment and update disbursement")
    void testProcessPayment_Success() {
        // Arrange
        Long disbursementId = 10L;
        PaymentDto dto = new PaymentDto(disbursementId, PaymentMethod.BANK, 500.0);

        Disbursement disbursement = new Disbursement();
        disbursement.setDisbursementID(disbursementId);
        disbursement.setStatus("PENDING");

        Payment savedPayment = new Payment();
        savedPayment.setPaymentID(100L);

        when(disbursementRepo.findById(disbursementId)).thenReturn(Optional.of(disbursement));
        when(paymentRepo.findByDisbursement_DisbursementID(disbursementId)).thenReturn(Optional.empty());
        when(paymentRepo.save(any(Payment.class))).thenReturn(savedPayment);

        // Act
        Payment result = paymentService.processPayment(dto);

        // Assert
        assertNotNull(result);
        assertEquals(100L, result.getPaymentID());
        assertEquals("COMPLETED", disbursement.getStatus()); // Verify status change logic

        verify(disbursementRepo, times(1)).save(disbursement);
        verify(paymentRepo, times(1)).save(any(Payment.class));
    }

    @Test
    @DisplayName("processPayment: Should throw exception when disbursement not found")
    void testProcessPayment_DisbursementNotFound() {
        PaymentDto dto = new PaymentDto(99L, PaymentMethod.BANK, 100.0);
        when(disbursementRepo.findById(99L)).thenReturn(Optional.empty());

        PaymentException ex = assertThrows(PaymentException.class, () -> paymentService.processPayment(dto));
        assertEquals(HttpStatus.NOT_FOUND, ex.getHttpStatus());
    }

    @Test
    @DisplayName("processPayment: Should throw exception if payment already exists (Conflict)")
    void testProcessPayment_AlreadyProcessed() {
        Long id = 1L;
        PaymentDto dto = new PaymentDto(id, PaymentMethod.WALLET, 100.0);

        when(disbursementRepo.findById(id)).thenReturn(Optional.of(new Disbursement()));
        when(paymentRepo.findByDisbursement_DisbursementID(id)).thenReturn(Optional.of(new Payment()));

        PaymentException ex = assertThrows(PaymentException.class, () -> paymentService.processPayment(dto));
        assertEquals(HttpStatus.CONFLICT, ex.getHttpStatus());
        assertEquals("Payment already processed for this disbursement", ex.getMessage());
    }

    // --- Tests for Other Methods ---

    @Test
    @DisplayName("getPaymentByDisbursement: Success")
    void testGetPaymentByDisbursement_Success() {
        Long id = 1L;
        Payment payment = new Payment();
        when(paymentRepo.findByDisbursement_DisbursementID(id)).thenReturn(Optional.of(payment));

        Payment result = paymentService.getPaymentByDisbursement(id);
        assertNotNull(result);
    }

    @Test
    @DisplayName("getPaymentByDisbursement: Not Found")
    void testGetPaymentByDisbursement_NotFound() {
        when(paymentRepo.findByDisbursement_DisbursementID(1L)).thenReturn(Optional.empty());
        assertThrows(PaymentException.class, () -> paymentService.getPaymentByDisbursement(1L));
    }

    @Test
    @DisplayName("deletePayment: Success")
    void testDeletePayment_Success() {
        Long id = 1L;
        when(paymentRepo.existsById(id)).thenReturn(true);

        paymentService.deletePayment(id);

        verify(paymentRepo, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("deletePayment: Not Found")
    void testDeletePayment_NotFound() {
        when(paymentRepo.existsById(1L)).thenReturn(false);
        assertThrows(PaymentException.class, () -> paymentService.deletePayment(1L));
    }

    @Test
    @DisplayName("getPaymentsByMethod: Return list")
    void testGetPaymentsByMethod() {
        when(paymentRepo.findByMethod(PaymentMethod.BANK)).thenReturn(List.of(new Payment()));
        List<Payment> results = paymentService.getPaymentsByMethod(PaymentMethod.BANK);
        assertEquals(1, results.size());
    }
    @Test
    @DisplayName("getAllPayments: Should return all records")
    void testGetAllPayments() {
        // Arrange
        List<Payment> mockList = List.of(new Payment(), new Payment());
        when(paymentRepo.findAll()).thenReturn(mockList);

        // Act
        List<Payment> result = paymentService.getAllPayments();

        // Assert
        assertEquals(2, result.size());
        verify(paymentRepo, times(1)).findAll();
    }
    @Test
    @DisplayName("getPaymentsByResearcher: Should return list of DTOs with correct mapping")
    void testGetPaymentsByResearcher_Success() {
        // Arrange
        Long researcherId = 50L;

        // Create nested mock data
        Disbursement disbursement = new Disbursement();
        disbursement.setDisbursementID(101L);
        disbursement.setAmount(1500.0);

        Payment payment = new Payment();
        payment.setMethod(PaymentMethod.BANK);
        payment.setDisbursement(disbursement);

        when(paymentRepo.findByDisbursement_Application_Researcher_ResearcherID(researcherId))
                .thenReturn(List.of(payment));

        // Act
        List<PaymentDto> results = paymentService.getPaymentsByResearcher(researcherId);

        // Assert
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());

        // Check if mapping logic in the Stream is correct
        PaymentDto dto = results.get(0);
        assertEquals(101L, dto.disbursementID());
        assertEquals(1500.0, dto.amount());
        assertEquals(PaymentMethod.BANK, dto.method());
    }

    @Test
    @DisplayName("getPaymentsByResearcher: Should return empty list when no payments found")
    void testGetPaymentsByResearcher_Empty() {
        // Arrange
        Long researcherId = 99L;
        when(paymentRepo.findByDisbursement_Application_Researcher_ResearcherID(researcherId))
                .thenReturn(List.of());

        // Act
        List<PaymentDto> results = paymentService.getPaymentsByResearcher(researcherId);

        // Assert
        assertTrue(results.isEmpty());
        verify(paymentRepo, times(1)).findByDisbursement_Application_Researcher_ResearcherID(researcherId);
    }
}