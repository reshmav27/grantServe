package com.cts.grantserve.service;

import com.cts.grantserve.dto.EvaluationDto;
import com.cts.grantserve.entity.Evaluation;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.enums.Result;
import com.cts.grantserve.exception.EvaluationNotFoundException;
import com.cts.grantserve.repository.EvaluationRepository;
import com.cts.grantserve.repository.IGrantApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EvaluationServiceImplTest {

    @Mock
    private EvaluationRepository evaluationRepository;

    @Mock
    private IGrantApplicationRepository applicationRepository;

    @InjectMocks
    private EvaluationServiceImpl evaluationService;

    private GrantApplication application;
    private Evaluation evaluation;
    private EvaluationDto evaluationDto;

    @BeforeEach
    void setUp() {
        // Prepare a Grant Application
        application = new GrantApplication();
        application.setApplicationID(101L);
        application.setStatus("PENDING_EVALUATION");

        // Prepare an Evaluation entity
        evaluation = new Evaluation();
        evaluation.setEvaluationID(1L);
        evaluation.setApplication(application);
        evaluation.setResult(Result.APPROVED);

        // Prepare the DTO for testing creation
        evaluationDto = new EvaluationDto(101L, Result.APPROVED, LocalDate.now(), "Project meets all criteria");
    }

    @Test
    @DisplayName("Create Evaluation - Successfully updates Application Status")
    void testCreateEvaluation_Success() {
        // Arrange
        when(applicationRepository.findById(101L)).thenReturn(Optional.of(application));
        when(evaluationRepository.save(any(Evaluation.class))).thenReturn(evaluation);

        // Act
        String result = evaluationService.createEvaluation(evaluationDto);

        // Assert
        assertTrue(result.contains("APPROVED"));
        assertEquals("APPROVED", application.getStatus()); // Verifies the sync logic
        verify(applicationRepository, times(1)).save(application);
        verify(evaluationRepository, times(1)).save(any(Evaluation.class));
    }

    @Test
    @DisplayName("Create Evaluation - Fails when Application ID is invalid")
    void testCreateEvaluation_NotFound() {
        // Arrange
        when(applicationRepository.findById(101L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                evaluationService.createEvaluation(evaluationDto)
        );
        assertEquals("Application not found", exception.getMessage());
    }

    @Test
    @DisplayName("Get Evaluation By ID - Success")
    void testGetEvaluationById_Success() {
        // Arrange
        when(evaluationRepository.findById(1L)).thenReturn(Optional.of(evaluation));

        // Act
        Evaluation result = evaluationService.getEvaluationById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getEvaluationID());
    }

    @Test
    @DisplayName("Get Evaluation By ID - Throws Custom Exception")
    void testGetEvaluationById_NotFound() {
        // Arrange
        when(evaluationRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        EvaluationNotFoundException exception = assertThrows(EvaluationNotFoundException.class, () ->
                evaluationService.getEvaluationById(1L)
        );
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    @DisplayName("Get All Evaluations - Success")
    void testGetAllEvaluations_Success() {
        // Arrange
        when(evaluationRepository.findAll()).thenReturn(List.of(evaluation));

        // Act
        List<Evaluation> result = evaluationService.getAllEvaluations();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Delete Evaluation - Success")
    void testDeleteEvaluation_Success() {
        // Arrange
        when(evaluationRepository.findById(1L)).thenReturn(Optional.of(evaluation));

        // Act
        String result = evaluationService.deleteEvaluation(1L);

        // Assert
        assertEquals("Evaluation record removed successfully", result);
        verify(evaluationRepository, times(1)).delete(evaluation);
    }
}