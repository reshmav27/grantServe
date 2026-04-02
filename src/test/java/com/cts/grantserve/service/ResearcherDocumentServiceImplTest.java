package com.cts.grantserve.service;

import com.cts.grantserve.dto.ResearcherDocumentDto;
import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.entity.ResearcherDocument;
import com.cts.grantserve.enums.DocType;
import com.cts.grantserve.exception.ResearcherDocumentException;
import com.cts.grantserve.repository.ResearcherDocumentRepository;
import com.cts.grantserve.repository.ResearcherRepository;
import org.junit.jupiter.api.BeforeEach;
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
public class ResearcherDocumentServiceImplTest {

    @Mock
    private ResearcherDocumentRepository researcherDocumentRepository;

    @Mock
    private ResearcherRepository researcherRepository;

    @InjectMocks
    private ResearcherDocumentServiceImpl researcherDocumentService;

    private Researcher researcher;
    private ResearcherDocument document;
    private ResearcherDocumentDto documentDto;
    private final Long resId = 1L;
    private final Long docId = 10L;

    @BeforeEach
    void setUp() {
        researcher = new Researcher();
        researcher.setResearcherID(resId);

        document = new ResearcherDocument();
        document.setDocumentID(docId);
        document.setResearcher(researcher);

        // Matching your DTO record structure
        documentDto = new ResearcherDocumentDto(resId, DocType.IDPROOF, "C:/uploads/id.pdf");
    }

    // --- uploadDocument Tests ---

    @Test
    void testUploadDocument_Success() throws ResearcherDocumentException {
        // Arrange
        when(researcherRepository.findById(resId)).thenReturn(Optional.of(researcher));
        when(researcherDocumentRepository.save(any(ResearcherDocument.class))).thenReturn(document);

        // Act
        String result = researcherDocumentService.uploadDocument(documentDto);

        // Assert
        assertEquals("Document Uploaded Successfully", result);
        verify(researcherDocumentRepository, times(1)).save(any(ResearcherDocument.class));
    }

    @Test
    void testUploadDocument_ResearcherNotFound() {
        // Arrange
        when(researcherRepository.findById(resId)).thenReturn(Optional.empty());

        // Act & Assert
        ResearcherDocumentException ex = assertThrows(ResearcherDocumentException.class, () ->
                researcherDocumentService.uploadDocument(documentDto)
        );
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    // --- getDocument Tests ---

    @Test
    void testGetDocument_Found() {
        // Arrange
        when(researcherDocumentRepository.findById(docId)).thenReturn(Optional.of(document));

        // Act
        Optional<ResearcherDocument> result = researcherDocumentService.getDocument(docId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(docId, result.get().getDocumentID());
    }

    // --- getAllDocuments Tests ---

    @Test
    void testGetAllDocuments_Success() {
        // Arrange
        when(researcherDocumentRepository.findAll()).thenReturn(List.of(document));

        // Act
        List<ResearcherDocument> result = researcherDocumentService.getAllDocuments();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    // --- deleteDocument Tests ---

    @Test
    void testDeleteDocument_Success() throws ResearcherDocumentException {
        // Arrange
        when(researcherDocumentRepository.findByDocumentIDAndResearcher_ResearcherID(docId, resId))
                .thenReturn(Optional.of(document));

        // Act
        String result = researcherDocumentService.deleteDocument(resId, docId);

        // Assert
        assertEquals("Document Deleted Successfully", result);
        verify(researcherDocumentRepository, times(1)).delete(document);
    }

    @Test
    void testDeleteDocument_NotFound() {
        // Arrange
        when(researcherDocumentRepository.findByDocumentIDAndResearcher_ResearcherID(docId, resId))
                .thenReturn(Optional.empty());

        // Act & Assert
        ResearcherDocumentException ex = assertThrows(ResearcherDocumentException.class, () ->
                researcherDocumentService.deleteDocument(resId, docId)
        );
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }
}