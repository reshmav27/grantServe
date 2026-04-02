package com.cts.grantserve.service;

import com.cts.grantserve.dto.ResearcherDto;
import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.exception.ResearcherException;
import com.cts.grantserve.projection.IResearcherProjection;
import com.cts.grantserve.repository.ResearcherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResearcherServiceImplTest {

    @Mock
    private ResearcherRepository researcherDAO;

    @InjectMocks
    private ResearcherServiceImpl researcherService;

    private Researcher researcher;
    private ResearcherDto researcherDto;
    private final Long researcherId = 1L;

    @BeforeEach
    void setUp() {
        researcher = new Researcher();
        researcher.setResearcherID(researcherId);

        researcherDto = new ResearcherDto(
                LocalDate.of(1990, 1, 1),
                "Male",
                "CTS University",
                "IT"
        );
    }

    // --- UpdateResearcher Tests ---
    @Test
    void testUpdateResearcher_Success() throws ResearcherException {
        when(researcherDAO.findById(researcherId)).thenReturn(Optional.of(researcher));

        String result = researcherService.UpdateResearcher(researcherId, researcherDto);

        assertEquals("Researcher Updated Successfully", result);
        verify(researcherDAO).save(any(Researcher.class));
    }

    @Test
    void testUpdateResearcher_NotFound() {
        when(researcherDAO.findById(researcherId)).thenReturn(Optional.empty());

        ResearcherException ex = assertThrows(ResearcherException.class, () ->
                researcherService.UpdateResearcher(researcherId, researcherDto)
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    // --- fetchResearcher Tests (Projections) ---
    @Test
    void testFetchResearcher_Success() throws ResearcherException {
        IResearcherProjection mockProjection = mock(IResearcherProjection.class);
        when(researcherDAO.findResearcherByResearcherID(researcherId)).thenReturn(Optional.of(mockProjection));

        IResearcherProjection result = researcherService.fetchResearcher(researcherId);

        assertNotNull(result);
    }

    @Test
    void testFetchResearcher_NotFound() {
        when(researcherDAO.findResearcherByResearcherID(researcherId)).thenReturn(Optional.empty());

        assertThrows(ResearcherException.class, () ->
                researcherService.fetchResearcher(researcherId)
        );
    }

    // --- getResearcher Tests (Internal Optional) ---
    @Test
    void testGetResearcher_Found() {
        when(researcherDAO.findById(researcherId)).thenReturn(Optional.of(researcher));

        Optional<Researcher> result = researcherService.getResearcher(researcherId);

        assertTrue(result.isPresent());
        assertEquals(researcherId, result.get().getResearcherID());
    }

    @Test
    void testGetResearcher_Empty() {
        when(researcherDAO.findById(researcherId)).thenReturn(Optional.empty());

        Optional<Researcher> result = researcherService.getResearcher(researcherId);

        assertFalse(result.isPresent());
    }
}