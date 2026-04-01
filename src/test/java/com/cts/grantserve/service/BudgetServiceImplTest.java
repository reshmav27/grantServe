package com.cts.grantserve.service;

import com.cts.grantserve.dto.BudgetDto;
import com.cts.grantserve.entity.Budget;
import com.cts.grantserve.entity.Program;
import com.cts.grantserve.enums.BudgetStatus;
import com.cts.grantserve.exception.*;
import com.cts.grantserve.projection.IBudgetProjection;
import com.cts.grantserve.repository.BudgetRepository;
import com.cts.grantserve.repository.ProgramRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BudgetServiceImplTest {

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private ProgramRepository programRepository;

    @InjectMocks
    private BudgetServiceImpl budgetService;

    @Test
    @DisplayName("Create Budget - Success")
    void createBudget_Success() {
        Program program = new Program();
        program.setProgramID(1L);
        BudgetDto dto = new BudgetDto(null, 1000.0, 0.0, 1000.0, BudgetStatus.ACTIVE, 1L);

        when(programRepository.findById(1L)).thenReturn(Optional.of(program));
        when(budgetRepository.save(any(Budget.class))).thenAnswer(i -> i.getArguments()[0]);

        BudgetDto result = budgetService.createBudget(dto);

        assertNotNull(result);
        assertEquals(1000.0, result.allocatedAmount());
        verify(budgetRepository).save(any(Budget.class));
    }

    @Test
    @DisplayName("Allocate Amount - Success")
    void allocateAmount_Success() {
        Budget budget = new Budget();
        budget.setBudgetID(1L);
        budget.setStatus(BudgetStatus.ACTIVE);
        budget.setSpentAmount(100.0);
        budget.setRemainingAmount(900.0);

        when(budgetRepository.findById(1L)).thenReturn(Optional.of(budget));

        String result = budgetService.allocateAmountToResearcherByBudgetId(1L, 200.0);

        assertEquals("Amount allocated to researcher successfully", result);
        assertEquals(300.0, budget.getSpentAmount());
        assertEquals(700.0, budget.getRemainingAmount());
        verify(budgetRepository).save(budget);
    }

    @Test
    @DisplayName("Allocate Amount - Insufficient Funds Exception")
    void allocateAmount_InsufficientFunds() {
        Budget budget = new Budget();
        budget.setRemainingAmount(50.0);
        when(budgetRepository.findById(1L)).thenReturn(Optional.of(budget));

        assertThrows(InsufficientFundsException.class, () ->
                budgetService.allocateAmountToResearcherByBudgetId(1L, 100.0));
    }

    @Test
    @DisplayName("Update Status to Closed - Success")
    void updateBudgetStatusToClosed_Success() {
        when(budgetRepository.updateBudgetStatusToClosed(1L)).thenReturn(1);

        String result = budgetService.updateBudgetStatusToClosed(1L);

        assertEquals("Budget status updated to CLOSED successfully.", result);
    }

    @Test
    @DisplayName("Update Status to Closed - ProgramNotModifiableException")
    void updateBudgetStatusToClosed_Failure() {
        when(budgetRepository.updateBudgetStatusToClosed(1L)).thenReturn(0);

        assertThrows(ProgramNotModifiableException.class, () ->
                budgetService.updateBudgetStatusToClosed(1L));
    }
}