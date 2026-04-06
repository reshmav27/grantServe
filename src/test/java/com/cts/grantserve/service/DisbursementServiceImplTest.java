package com.cts.grantserve.service;

import com.cts.grantserve.dto.DisbursementDto;
import com.cts.grantserve.entity.*;
import com.cts.grantserve.exception.DisbursementException;
import com.cts.grantserve.repository.AllocationRepository;
import com.cts.grantserve.repository.DisbursementRepository;
import com.cts.grantserve.repository.IGrantApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
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
    private AllocationRepository allocationRepo; // Switched from budgetRepo

    @Mock
    private IGrantApplicationRepository applicationRepo;

    @InjectMocks
    private DisbursementServiceImpl disbursementService;

    private DisbursementDto dto;
    private Allocation allocation;
    private GrantApplication application;

    @BeforeEach
    void setUp() {
        // Sample data for 1000.0 allocated, 500.0 requested
        dto = new DisbursementDto(1L, 101L, 500.0);

        application = new GrantApplication();
        application.setApplicationID(1L);

        allocation = new Allocation();
        allocation.setTotalAwardedAmount(1000.0);
        allocation.setDisbursedAmount(0.0);
        allocation.setRemainingBalance(1000.0);
        allocation.setStatus("ALLOCATED");
    }

    @Test
    @DisplayName("Should successfully initiate disbursement and update allocation balance")
    void testInitiateDisbursement_Success() {
        // Arrange
        when(allocationRepo.findByApplicationApplicationID(1L)).thenReturn(Optional.of(allocation));
        when(applicationRepo.findById(1L)).thenReturn(Optional.of(application));

        Disbursement mockSaved = new Disbursement();
        mockSaved.setDisbursementID(99L);
        when(disbursementRepo.save(any(Disbursement.class))).thenReturn(mockSaved);

        // Act
        Disbursement result = disbursementService.initiateDisbursement(dto);

        // Assert
        assertNotNull(result);
        assertEquals(500.0, allocation.getDisbursedAmount());
        assertEquals(500.0, allocation.getRemainingBalance());
        verify(allocationRepo, times(1)).save(allocation);
        verify(disbursementRepo, times(1)).save(any(Disbursement.class));
    }

    @Test
    @DisplayName("Should throw exception when allocation record is missing")
    void testInitiateDisbursement_NoAllocation() {
        // Arrange
        when(allocationRepo.findByApplicationApplicationID(1L)).thenReturn(Optional.empty());

        // Act & Assert
        DisbursementException ex = assertThrows(DisbursementException.class, () -> {
            disbursementService.initiateDisbursement(dto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, ex.getHttpStatus());
        assertTrue(ex.getMessage().contains("Allocation must be initiated first"));
    }

    @Test
    @DisplayName("Should throw exception when requested amount exceeds allocation balance")
    void testInitiateDisbursement_InsufficientAllocation() {
        // Arrange: Requesting 1500 when only 1000 is available
        DisbursementDto overLimitDto = new DisbursementDto(1L, 101L, 1500.0);
        when(allocationRepo.findByApplicationApplicationID(1L)).thenReturn(Optional.of(allocation));

        // Act & Assert
        DisbursementException ex = assertThrows(DisbursementException.class, () -> {
            disbursementService.initiateDisbursement(overLimitDto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, ex.getHttpStatus());
        assertTrue(ex.getMessage().contains("Insufficient allocated funds"));
    }



    @Test
    @DisplayName("trackByApplication: Should return list of disbursements")
    void testTrackByApplication() {
        // Arrange
        Long appId = 1L;
        List<Disbursement> mockList = List.of(new Disbursement(), new Disbursement());
        when(disbursementRepo.findByApplication_ApplicationID(appId)).thenReturn(mockList);

        // Act
        List<Disbursement> result = disbursementService.trackByApplication(appId);

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
        List<Disbursement> result = disbursementService.trackByStatus(status);

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
        disbursementService.deleteDisbursement(id);

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
            disbursementService.deleteDisbursement(id);
        });

        assertEquals(HttpStatus.NOT_FOUND, ex.getHttpStatus());
        verify(disbursementRepo, never()).deleteById(anyLong());
    }
}