package com.cts.grantserve.service;

import com.cts.grantserve.dto.ResearcherDto;
import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.exception.ResearcherException;
import com.cts.grantserve.repository.ResearcherRepository;
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
public class ResearcherServiceImplTest {

    @Mock
    private ResearcherRepository researcherRepository;

    @InjectMocks
    private ResearcherServiceImpl researcherService;

    @Test
    void testUpdateResearcher_Success() throws ResearcherException {
        // 1. Arrange: Setup fake data
        Long id = 1L;
        Researcher existingResearcher = new Researcher();
        existingResearcher.setResearcherID(id);

        // Corrected DTO to match your record: dob, gender, institution, department
        ResearcherDto dto = new ResearcherDto(
                LocalDate.of(1995, 1, 1),
                "Male",
                "ABC University",
                "Computer Science"
        );

        when(researcherRepository.findById(id)).thenReturn(Optional.of(existingResearcher));

        // 2. Act
        String response = researcherService.UpdateResearcher(id, dto);

        // 3. Assert
        assertEquals("Researcher Updated Successfully", response);
        verify(researcherRepository, times(1)).save(any(Researcher.class));
    }

    @Test
    void testUpdateResearcher_NotFound() {
        Long id = 1L;
        when(researcherRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResearcherException.class, () -> {
            researcherService.UpdateResearcher(id, null);
        });
    }
}