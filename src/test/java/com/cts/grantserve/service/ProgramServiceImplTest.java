package com.cts.grantserve.service;

import com.cts.grantserve.dto.BudgetDto;
import com.cts.grantserve.dto.ProgramDto;
import com.cts.grantserve.entity.Budget;
import com.cts.grantserve.entity.Program;
import com.cts.grantserve.enums.BudgetStatus;
import com.cts.grantserve.enums.ProgramStatus;
import com.cts.grantserve.exception.ProgramNotFoundException;
import com.cts.grantserve.exception.ProgramNotModifiableException;
import com.cts.grantserve.repository.ProgramRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProgramServiceImplTest {

    @Mock
    private ProgramRepository programRepository;

    @Mock
    private IBudgetService budgetService;

    @InjectMocks
    private ProgramServiceImpl programService;

    @Test
    @DisplayName("Create Program - Active Status triggers Budget Initialization")
    void createProgram_ActiveStatus() {
        ProgramDto dto = new ProgramDto(1L, "Title", "Desc", LocalDate.now(), LocalDate.now().plusDays(20), 5000.0, ProgramStatus.ACTIVE);
        Program savedProgram = new Program();
        savedProgram.setProgramID(1L);
        savedProgram.setStatus(ProgramStatus.ACTIVE);
        savedProgram.setBudget(5000.0);

        when(programRepository.save(any(Program.class))).thenReturn(savedProgram);

        programService.createProgram(dto);

        // Verify that budget initialization was triggered
        verify(budgetService, times(1)).createBudget(any(BudgetDto.class));
    }

    @Test
    @DisplayName("Update Program - Only DRAFT programs can be modified")
    void updateProgram_NonDraftThrowsException() {
        ProgramDto dto = new ProgramDto(1L, "Title", "Desc", LocalDate.now(), LocalDate.now().plusDays(20), 5000.0, ProgramStatus.ACTIVE);
        Program existing = new Program();
        existing.setStatus(ProgramStatus.ACTIVE); // Not DRAFT

        when(programRepository.findById(1L)).thenReturn(Optional.of(existing));

        assertThrows(ProgramNotModifiableException.class, () -> programService.updateProgram(dto));
    }

    @Test
    @DisplayName("Update Program Status to Closed - Success")
    void updateProgramStatusToClosed_Success() {
        Long id = 1L;
        when(programRepository.updateProgramStatusToClosed(id)).thenReturn(1);
        when(budgetService.updateBudgetStatusToClosed(id)).thenReturn("Budget status updated to CLOSED successfully.");

        String result = programService.updateProgramStatusToClosed(id);

        assertTrue(result.contains("Program status updated to CLOSED successfully."));
        verify(programRepository).updateProgramStatusToClosed(id);
        verify(budgetService).updateBudgetStatusToClosed(id);
    }

    @Test
    @DisplayName("Update Program Status to Closed - Fails when Program ID Invalid")
    void updateProgramStatusToClosed_Failure() {
        when(programRepository.updateProgramStatusToClosed(1L)).thenReturn(0);

        assertThrows(ProgramNotModifiableException.class, () ->
                programService.updateProgramStatusToClosed(1L));
    }
}