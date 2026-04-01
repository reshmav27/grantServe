package com.cts.grantserve.service;

import com.cts.grantserve.dto.ResearcherDocumentDto;
import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.entity.ResearcherDocument;
import com.cts.grantserve.enums.DocType;
import com.cts.grantserve.exception.ResearcherDocumentException;
import com.cts.grantserve.repository.ResearcherDocumentRepository;
import com.cts.grantserve.repository.ResearcherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResearcherDocumentServiceImplTest {

    @Mock
    private ResearcherDocumentRepository documentRepo;

    @Mock
    private ResearcherRepository researcherRepo;

    @InjectMocks
    private ResearcherDocumentServiceImpl documentService;

    @Test
    void testUploadDocument_Success() throws ResearcherDocumentException {
        // Arrange
        Long resId = 1L;
        ResearcherDocumentDto dto = new ResearcherDocumentDto(resId, DocType.IDPROOF, "C:/docs/id.pdf");

        Researcher mockResearcher = new Researcher();
        mockResearcher.setResearcherID(resId);

        // Service requires finding the researcher first
        when(researcherRepo.findById(resId)).thenReturn(Optional.of(mockResearcher));

        // Act
        String response = documentService.uploadDocument(dto);

        // Assert
        assertEquals("Document Uploaded Successfully", response);
        verify(documentRepo, times(1)).save(any(ResearcherDocument.class));
    }

    @Test
    void testDeleteDocument_Success() throws ResearcherDocumentException {
        // Arrange
        Long resId = 1L;
        Long docId = 10L;
        ResearcherDocument mockDoc = new ResearcherDocument();

        // Repository uses custom find method
        when(documentRepo.findByDocumentIDAndResearcher_ResearcherID(docId, resId))
                .thenReturn(Optional.of(mockDoc));

        // Act
        String response = documentService.deleteDocument(resId, docId);

        // Assert
        assertEquals("Document Deleted Successfully", response);
        verify(documentRepo, times(1)).delete(mockDoc);
    }
}