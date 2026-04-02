package com.cts.grantserve.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.cts.grantserve.dto.GrantApplicationDto;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.entity.Program;
import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.exception.GrantApplicationException;
import com.cts.grantserve.exception.ProgramNotFoundException;
import com.cts.grantserve.exception.ResearcherException;
import com.cts.grantserve.repository.IGrantApplicationRepository;
import com.cts.grantserve.repository.ProgramRepository;
import com.cts.grantserve.repository.ResearcherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

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

    // Move these to class level so all test methods can see them
    private Long researcherId;
    private Long programId;
    private GrantApplicationDto dto;

    @BeforeEach
    void setValues() {
        researcherId = 1L;
        programId = 101L;
        dto = new GrantApplicationDto(researcherId, programId, "New Proposal");
    }
    @Test
    void testRegisterGrantApplication_Success(){
        when(programRepository.findById(programId)).thenReturn(Optional.of(new Program()));
        when(researcherRepository.findById(researcherId)).thenReturn(Optional.of(new Researcher()));
        String result = grantApplicationService.createApplication(dto);
        assertEquals("Created SuccessFully",result);
        verify(grantApplicationDao).save(argThat(app ->
                "Active".equals(app.getStatus()) &&
                        app.getSubmittedDate().equals(LocalDate.now())
        ));
        verify(grantApplicationDao,times(1)).save(any(GrantApplication.class));
    }
    @Test
    void researcherNotFoundTest() throws GrantApplicationException {
        when(researcherRepository.findById(researcherId)).thenReturn(Optional.empty());
        assertThrows(ResearcherException.class, () ->
        {
            grantApplicationService.createApplication(dto);
        });
    }

    @Test
    void programNotFoundTest() throws GrantApplicationException{
        when(researcherRepository.findById(researcherId)).thenReturn(Optional.of(new Researcher()));
        when(programRepository.findById(programId)).thenReturn(Optional.empty());
        assertThrows(ProgramNotFoundException.class,()->{
            grantApplicationService.createApplication(dto);
        });
    }

    @Test
    void testIfGrantApplicationDto_Null(){
        assertThrows(NullPointerException.class,()->{
            grantApplicationService.createApplication(null);
        });
    }

    @Test
    void testGrantApplicationDelete_success(){
        when(grantApplicationDao.existsById(1L)).thenReturn(true);
        String result = grantApplicationService.DeleteApplication(1L);
        assertEquals("Deleted SuccessFully",result);
        verify(grantApplicationDao,times(1)).deleteById(1L);
    }

    @Test
    void testGrantApplicationDelete_fail(){
        when(grantApplicationDao.existsById(1L)).thenReturn(false);
        assertThrows(GrantApplicationException.class,()->{
            grantApplicationService.DeleteApplication(1L);
        });
    }

    @Test
    void testDeleteApplication_NullId() {
        assertThrows(Exception.class, () -> {
            grantApplicationService.DeleteApplication(null);
        });
    }

    @Test
    void testFetchGrantApplication_Success(){
        GrantApplication grantApplication = new GrantApplication();
        grantApplication.setApplicationID(1L);
        when(grantApplicationDao.findById(1L)).thenReturn(Optional.of(grantApplication));
        GrantApplication application = grantApplicationService.getApplication(1L);
        assertEquals(1L,grantApplication.getApplicationID());
        assertNotNull(application);
        verify(grantApplicationDao, times(1)).findById(1L);
    }

    @Test
    void testFetchGrantApplication_fail(){
        when(grantApplicationDao.findById(1L)).thenReturn(Optional.empty());
        GrantApplicationException ex = assertThrows(GrantApplicationException.class,()->
        {
            grantApplicationService.getApplication(1L);
        });

        assertTrue(ex.getMessage().contains("Application not found for ID: " + 1L));
    }

    @Test
    void testIfGrantApplicationId_null(){
        assertThrows(Exception.class,()->grantApplicationService.DeleteApplication(null));
    }

    @Test
    void testSearch_ByIdOnly() {
        // Arrange
        List<GrantApplication> mockList = List.of(new GrantApplication());
        // We use any(Specification.class) because the Specification object is created inside the method
        when(grantApplicationDao.findAll(any(Specification.class))).thenReturn(mockList);

        // Act
        List<GrantApplication> result = grantApplicationService.search(1L, null);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(grantApplicationDao, times(1)).findAll(any(Specification.class));
    }

    @Test
    void testSearch_NoResults() {
        // Arrange: Return an empty list
        when(grantApplicationDao.findAll(any(Specification.class))).thenReturn(List.of());

        // Act
        List<GrantApplication> result = grantApplicationService.search(999L, "NonExistent");

        // Assert
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }

    @Test
    void testFetchGrantApplicationByResearcher_Success() throws GrantApplicationException {
        Long researcherId = 1L;
        GrantApplication app = new GrantApplication();
        app.setApplicationID(101L);
        List<GrantApplication> mockList = List.of(app);
        when(grantApplicationDao.findByResearcher_ResearcherID(researcherId))
                .thenReturn(Optional.of(mockList));

        // Act
        List<GrantApplication> result = grantApplicationService.FetchGrantApplication(researcherId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(101L, result.get(0).getApplicationID());
        verify(grantApplicationDao, times(1)).findByResearcher_ResearcherID(researcherId);
    }

    @Test
    void testFetchGrantApplication_NotFound() {
        // Arrange
        Long researcherId = 1L;
        when(grantApplicationDao.findByResearcher_ResearcherID(researcherId))
                .thenReturn(Optional.empty());

        GrantApplicationException ex = assertThrows(GrantApplicationException.class, () -> {
            grantApplicationService.FetchGrantApplication(researcherId);
        });

        assertTrue(ex.getMessage().contains("No Grant Applications found for the user " + researcherId));
    }
    @Test
    void testGetuserApplicationCount_Success() throws GrantApplicationException {
        // Arrange
        Long researcherId = 1L;
        // Step 1: Mock the "Gatekeeper" check as successful
        when(grantApplicationDao.findByResearcher(researcherId))
                .thenReturn(Optional.of(new Researcher())); // Assuming it returns Researcher

        // Step 2: Mock the actual count
        when(grantApplicationDao.countByResearcher_ResearcherID(researcherId))
                .thenReturn(5L);

        // Act
        Long actualCount = grantApplicationService.getuserApplicationCount(researcherId);

        // Assert
        assertEquals(5L, actualCount);
        verify(grantApplicationDao).findByResearcher(researcherId);
        verify(grantApplicationDao,times(1)).countByResearcher_ResearcherID(researcherId);
    }

    @Test
    void testGetuserApplicationCount_ResearcherNotFound() {
        // Arrange
        Long researcherId = 1L;
        // Make the first check fail
        when(grantApplicationDao.findByResearcher(researcherId))
                .thenReturn(Optional.empty());

        // Act & Assert
        GrantApplicationException ex = assertThrows(GrantApplicationException.class, () -> {
            grantApplicationService.getuserApplicationCount(researcherId);
        });

        assertTrue(ex.getMessage().contains("Researcher Not found with id " + researcherId));

        // Verify that the count method was NEVER called (Safety Check)
        verify(grantApplicationDao, never()).countByResearcher_ResearcherID(anyLong());
    }

    @Test
    void testFetchProgramGrantApplications_Success() throws GrantApplicationException {
        // Arrange
        Long programId = 101L;
        List<GrantApplication> mockApps = List.of(new GrantApplication());

        // UPDATE THIS LINE: Use the new method name
        when(grantApplicationDao.findByProgram_ProgramID(programId))
                .thenReturn(Optional.of(mockApps));

        // Act
        Optional<List<GrantApplication>> result = grantApplicationService.fetchProgramGrantApplications(programId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());

        // UPDATE THIS LINE: Verify the correct interaction
        verify(grantApplicationDao).findByProgram_ProgramID(programId);
    }

    @Test
    void testFetchProgramGrantApplications_NotFound() {
        // Arrange
        Long programId = 101L;

        // UPDATE THIS LINE: Match the new method name
        when(grantApplicationDao.findByProgram_ProgramID(programId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(GrantApplicationException.class, () -> {
            grantApplicationService.fetchProgramGrantApplications(programId);
        });
    }
}