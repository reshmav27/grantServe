package com.cts.grantserve.service;


import com.cts.grantserve.dto.ProposalDto;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.exception.ProposalException;
import com.cts.grantserve.projection.IProposalProjection;
import com.cts.grantserve.repository.IGrantApplicationRepository;
import com.cts.grantserve.repository.IProposalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProposalServiceImplTest {

    @Mock
    IGrantApplicationRepository grantApplicationRepository;

    @Mock
    IProposalRepository proposalRepository;

    @Mock
    IProposalProjection mockProjection;

    @InjectMocks
    ProposalServiceImpl proposalService;

    @Test
    void testCreateProposal_Success() throws ProposalException {
        // Arrange
        Long appId = 1L;
        ProposalDto dto = new ProposalDto(appId, "Research Proposal");
        GrantApplication mockApp = new GrantApplication();

        when(grantApplicationRepository.findById(appId)).thenReturn(Optional.of(mockApp));
        // We don't need to return anything specific from save for a String return type,
        // but we can verify the behavior.

        // Act
        String result = proposalService.createProposal(dto);

        // Assert
        assertTrue(result.contains("Proposal submitted successfully"));
        verify(grantApplicationRepository).findById(appId);
        // Verify status was set to SUBMITTED and the app was linked
        verify(proposalRepository).save(argThat(proposal ->
                "SUBMITTED".equals(proposal.getStatus()) &&
                        proposal.getGrantApplication().equals(mockApp)
        ));
    }

    @Test
    void testCreateProposal_ApplicationNotFound() {
        // Arrange
        Long appId = 99L;
        ProposalDto dto = new ProposalDto(appId, "Invalid App");
        when(grantApplicationRepository.findById(appId)).thenReturn(Optional.empty());

        // Act & Assert
        ProposalException ex = assertThrows(ProposalException.class, () -> {
            proposalService.createProposal(dto);
        });

        assertEquals("Application Not found", ex.getMessage());
        verify(proposalRepository, never()).save(any(Proposal.class));
    }
    @Test
    void testGetRecentProposals_Success_ReturnsThree() throws ProposalException {
        // Arrange
        Long appId = 1L;
        List<IProposalProjection> mockList = List.of(mockProjection,mockProjection,mockProjection);

        when(grantApplicationRepository.existsById(appId)).thenReturn(true);
        when(proposalRepository.findTop3ByGrantApplication_ApplicationIDOrderByProposalIDDesc(appId))
                .thenReturn(mockList);

        // Act
        List<IProposalProjection> result = proposalService.getRecentProposals(appId);

        // Assert
        assertEquals(3, result.size());
        verify(grantApplicationRepository).existsById(appId);
        verify(proposalRepository).findTop3ByGrantApplication_ApplicationIDOrderByProposalIDDesc(appId);
    }

    @Test
    void testGetRecentProposals_Success_ReturnsPartial() throws ProposalException {
        // Arrange: Only 1 proposal exists
        Long appId = 1L;
        List<IProposalProjection> mockList = List.of(mockProjection);

        when(grantApplicationRepository.existsById(appId)).thenReturn(true);
        when(proposalRepository.findTop3ByGrantApplication_ApplicationIDOrderByProposalIDDesc(appId))
                .thenReturn(mockList);

        // Act
        List<IProposalProjection> result = proposalService.getRecentProposals(appId);

        // Assert
        assertEquals(1, result.size());
    }
    @Test
    void testGetRecentProposals_EmptyList() throws ProposalException {
        // Arrange
        Long appId = 1L;
        when(grantApplicationRepository.existsById(appId)).thenReturn(true);
        when(proposalRepository.findTop3ByGrantApplication_ApplicationIDOrderByProposalIDDesc(appId))
                .thenReturn(List.of()); // Returns empty list
        // Act
        List<IProposalProjection> result = proposalService.getRecentProposals(appId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetRecentProposals_ApplicationNotFound() {
        // Arrange
        Long appId = 999L;
        when(grantApplicationRepository.existsById(appId)).thenReturn(false);

        // Act & Assert
        ProposalException ex = assertThrows(ProposalException.class, () -> {
            proposalService.getRecentProposals(appId);
        });

        assertEquals("Application not found with ID: " + appId, ex.getMessage());
        // Pro-Tip: Verify the DAO was NEVER called if the app was missing
        verify(proposalRepository, never()).findTop3ByGrantApplication_ApplicationIDOrderByProposalIDDesc(anyLong());
    }
    @Test
    void testGetRecentProposals_NullId() {
        // Depending on your logic, this might throw an exception or return empty
        // If your repo.existsById throws on null, this will too
        assertThrows(ProposalException.class, () -> {
            proposalService.getRecentProposals(null);
        });
    }

    @Test
    void testGetProposal_ProjectionSuccess() {

        Long proposalId = 50L;

        List<IProposalProjection> mockList = List.of(mockProjection);

        when(proposalRepository.findProjectedById(proposalId)).thenReturn(mockList);

        // Act
        List<IProposalProjection> result = proposalService.getProposal(proposalId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(proposalRepository).findProjectedById(proposalId);
    }
}
