package com.cts.grantserve.service;

import com.cts.grantserve.dto.DisbursementDto;
import com.cts.grantserve.entity.Budget;
import com.cts.grantserve.entity.Disbursement;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.entity.Program;
import com.cts.grantserve.exception.DisbursementException;
import com.cts.grantserve.repository.BudgetRepository;
import com.cts.grantserve.repository.DisbursementRepository;
import com.cts.grantserve.repository.IGrantApplicationRepository;
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
public class DisbursementServiceImplTest {

    @Mock
    private DisbursementRepository disbursementRepo;

    @Mock
    private BudgetRepository budgetRepo;

    @Mock
    private IGrantApplicationRepository applicationRepo;

    @InjectMocks
    private DisbursementServiceImpl disbursementServiceImpl;

    @Test
    @DisplayName("Should successfully initiate disbursement when budget and application exist")
    public void testInitiateDisbursement_Success() {
        // Arrange
        Long appId = 1L;
        Long programId = 1L;
        Double amount = 1000.0;
        DisbursementDto dto = new DisbursementDto(appId, programId, amount);

        Budget budget = new Budget();
        budget.setAllocatedAmount(5000.0);
        budget.setSpentAmount(1000.0);
        budget.setRemainingAmount(4000.0);

        GrantApplication application = new GrantApplication();
        application.setApplicationID(appId);

        Disbursement savedDisbursement = new Disbursement();
        savedDisbursement.setDisbursementID(101L);
        savedDisbursement.setAmount(amount);

        // Mocking dependencies
        when(budgetRepo.findByProgramProgramID(programId)).thenReturn(Optional.of(budget));
        when(applicationRepo.findById(appId)).thenReturn(Optional.of(application));
        when(disbursementRepo.save(any(Disbursement.class))).thenReturn(savedDisbursement);

        // Act
        Disbursement result = disbursementServiceImpl.initiateDisbursement(dto);

        // Assert
        assertNotNull(result);
        assertEquals(101L, result.getDisbursementID());
        assertEquals(1000.0, result.getAmount());

        // Verify budget update logic (Spent: 1000 + 1000 = 2000)
        assertEquals(2000.0, budget.getSpentAmount());
        assertEquals(3000.0, budget.getRemainingAmount());

        verify(budgetRepo, times(1)).save(budget);
        verify(disbursementRepo, times(1)).save(any(Disbursement.class));
    }

    @Test
    @DisplayName("Should throw exception when budget is insufficient")
    public void testInitiateDisbursement_InsufficientFunds() {
        // Arrange
        DisbursementDto dto = new DisbursementDto(1L, 1L, 1000.0);
        Budget budget = new Budget();
        budget.setRemainingAmount(500.0); // Less than 1000.0

        when(budgetRepo.findByProgramProgramID(1L)).thenReturn(Optional.of(budget));

        // Act & Assert
        DisbursementException exception = assertThrows(DisbursementException.class, () -> {
            disbursementServiceImpl.initiateDisbursement(dto);
        });

        assertTrue(exception.getMessage().contains("Insufficient funds"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        verify(disbursementRepo, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when application is not found")
    public void testInitiateDisbursement_ApplicationNotFound() {
        // 1. Arrange
        Long appId = 99L;
        Long programId = 1L;
        Double amount = 1000.0;
        DisbursementDto dto = new DisbursementDto(appId, programId, amount);

        Budget budget = new Budget();
        Program program=new Program();
        program.setProgramID(programId);
        budget.setProgram(program);
        // CRITICAL: Initialize these to avoid NullPointerException during unboxing
        budget.setAllocatedAmount(5000.0);
        budget.setSpentAmount(0.0);
        budget.setRemainingAmount(5000.0);

        // Mock the budget check (this happens BEFORE the application check in your service)
        when(budgetRepo.findByProgramProgramID(programId)).thenReturn(Optional.of(budget));

        // Mock the application check to return empty
        when(applicationRepo.findById(appId)).thenReturn(Optional.empty());

        // 2. Act & 3. Assert
        DisbursementException exception = assertThrows(DisbursementException.class, () -> {
            disbursementServiceImpl.initiateDisbursement(dto);
        });

        // Verify it's the correct error
        assertEquals("Application not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }
    @Test
    @DisplayName("reconcileBudget: Should update budget successfully")
    void testReconcileBudget_Success() {
        // Arrange
        Long programId = 1L;
        Double amount = 500.0;
        Budget budget = new Budget();
        budget.setAllocatedAmount(2000.0);
        budget.setSpentAmount(500.0);
        budget.setRemainingAmount(1500.0);

        when(budgetRepo.findByProgramProgramID(programId)).thenReturn(Optional.of(budget));

        // Act
        disbursementServiceImpl.reconcileBudget(programId, amount);

        // Assert
        // New Spent: 500 + 500 = 1000 | New Remaining: 2000 - 1000 = 1000
        assertEquals(1000.0, budget.getSpentAmount());
        assertEquals(1000.0, budget.getRemainingAmount());
        verify(budgetRepo, times(1)).save(budget);
    }

    @Test
    @DisplayName("reconcileBudget: Should throw exception for insufficient funds")
    void testReconcileBudget_InsufficientFunds() {
        // Arrange
        Long programId = 1L;
        Double amount = 5000.0; // Higher than available
        Budget budget = new Budget();
        budget.setRemainingAmount(1000.0);

        when(budgetRepo.findByProgramProgramID(programId)).thenReturn(Optional.of(budget));

        // Act & Assert
        DisbursementException ex = assertThrows(DisbursementException.class, () -> {
            disbursementServiceImpl.reconcileBudget(programId, amount);
        });

        assertTrue(ex.getMessage().contains("Insufficient funds"));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getHttpStatus());
    }
    @Test
    @DisplayName("trackByApplication: Should return list of disbursements")
    void testTrackByApplication() {
        // Arrange
        Long appId = 1L;
        List<Disbursement> mockList = List.of(new Disbursement(), new Disbursement());
        when(disbursementRepo.findByApplication_ApplicationID(appId)).thenReturn(mockList);

        // Act
        List<Disbursement> result = disbursementServiceImpl.trackByApplication(appId);

        // Assert
        assertEquals(2, result.size());
        verify(disbursementRepo, times(1)).findByApplication_ApplicationID(appId);
    }

    @Test
    @DisplayName("trackByStatus: Should return filtered list")
    void testTrackByStatus() {
        // Arrange
        String status = "COMPLETED";
        when(disbursementRepo.findByStatus(status)).thenReturn(List.of(new Disbursement()));

        // Act
        List<Disbursement> result = disbursementServiceImpl.trackByStatus(status);

        // Assert
        assertFalse(result.isEmpty());
        verify(disbursementRepo, times(1)).findByStatus(status);
    }
    @Test
    @DisplayName("deleteDisbursement: Should delete when ID exists")
    void testDeleteDisbursement_Success() {
        // Arrange
        Long id = 1L;
        when(disbursementRepo.existsById(id)).thenReturn(true);

        // Act
        disbursementServiceImpl.deleteDisbursement(id);

        // Assert
        verify(disbursementRepo, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("deleteDisbursement: Should throw exception when ID missing")
    void testDeleteDisbursement_NotFound() {
        // Arrange
        Long id = 999L;
        when(disbursementRepo.existsById(id)).thenReturn(false);

        // Act & Assert
        DisbursementException ex = assertThrows(DisbursementException.class, () -> {
            disbursementServiceImpl.deleteDisbursement(id);
        });

        assertEquals(HttpStatus.NOT_FOUND, ex.getHttpStatus());
        verify(disbursementRepo, never()).deleteById(anyLong());
    }
}