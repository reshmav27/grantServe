package com.cts.grantserve;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import com.cts.grantserve.dto.GrantApplicationDto;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.entity.Program;
import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.exception.GrantApplicationException;
import com.cts.grantserve.exception.ProgramNotFoundException;
import com.cts.grantserve.exception.ProposalException;
import com.cts.grantserve.exception.ResearcherException;
import com.cts.grantserve.repository.IGrantApplicationRepository;
import com.cts.grantserve.repository.ProgramRepository;
import com.cts.grantserve.repository.ResearcherRepository;
import com.cts.grantserve.service.GrantApplicationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GrantApplicationServiceImplTest {

    @Mock
    private IGrantApplicationRepository grantApplicationDao;

    @Mock
    private ProgramRepository programRepository;

    @Mock
    private ResearcherRepository researcherRepository;

    @InjectMocks
    private GrantApplicationServiceImpl grantApplicationService;

    @Test
    void testRegisterGrantApplication_Success(){
        Long researcherId = 1L;
        Long programId = 101L;
        GrantApplicationDto dto = new GrantApplicationDto(researcherId, programId, "New Proposal");
        when(programRepository.findById(programId)).thenReturn(Optional.of(new Program()));
        when(researcherRepository.findById(researcherId)).thenReturn(Optional.of(new Researcher()));
        String result = grantApplicationService.createApplication(dto);
        assertEquals("Created SuccessFully",result);
        verify(grantApplicationDao,times(1)).save(any(GrantApplication.class));
    }


}