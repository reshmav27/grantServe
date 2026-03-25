package com.cts.grantserve.service;

import com.cts.grantserve.dto.ResearcherDocumentDto;
import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.entity.ResearcherDocument;
import com.cts.grantserve.exception.ResearcherDocumentException;
import com.cts.grantserve.repository.ResearcherDocumentRepository;
import com.cts.grantserve.repository.ResearcherRepository;
import com.cts.grantserve.util.ClassUtilSeparator;
import lombok.extern.slf4j.Slf4j; // 1. Import Slf4j
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ResearcherDocumentServiceImpl implements IResearcherDocumentService {

    @Autowired
    private ResearcherDocumentRepository researcherDocumentRepository;

    @Autowired
    private ResearcherRepository researcherRepository;

    @Override
    public String uploadDocument(ResearcherDocumentDto documentDto) throws ResearcherDocumentException {
        log.info("Initiating document upload for Researcher ID: {}", documentDto.researcherID());

        // 1. Convert DTO to Entity
        ResearcherDocument doc = ClassUtilSeparator.documentUploadUtil(documentDto);

        // 2. Look up the Researcher
        Researcher researcher = researcherRepository.findById(documentDto.researcherID())
                .orElseThrow(() -> {
                    log.error("Upload failed: Researcher ID {} not found", documentDto.researcherID());
                    return new ResearcherDocumentException(
                            "Cannot upload document. Researcher not found with ID: " + documentDto.researcherID(),
                            HttpStatus.NOT_FOUND);
                });

        // 3. Link & Set Metadata
        doc.setResearcher(researcher);
        doc.setUploadedDate(LocalDateTime.now());
        doc.setVerificationStatus("Pending");

        // 4. Save
        researcherDocumentRepository.save(doc);

        log.info("Document successfully uploaded and linked to Researcher ID: {}", researcher.getResearcherID());
        return "Document Uploaded Successfully";
    }

    @Override
    public Optional<ResearcherDocument> getDocument(Long id) {
        log.debug("Fetching document details for Document ID: {}", id);
        return researcherDocumentRepository.findById(id);
    }

    @Override
    public List<ResearcherDocument> getAllDocuments() {
        log.info("Retrieving all documents from the system");
        return researcherDocumentRepository.findAll();
    }

    @Override
    public String deleteDocument(Long researcherId, Long documentId) throws ResearcherDocumentException {
        log.info("Attempting to delete Document ID: {} for Researcher ID: {}", documentId, researcherId);

        ResearcherDocument doc = researcherDocumentRepository
                .findByDocumentIDAndResearcher_ResearcherID(documentId, researcherId)
                .orElseThrow(() -> {
                    log.warn("Delete failed: Document ID {} not found for Researcher ID {}", documentId, researcherId);
                    return new ResearcherDocumentException(
                            "Document not found or does not belong to researcher ID: " + researcherId,
                            HttpStatus.NOT_FOUND);
                });

        researcherDocumentRepository.delete(doc);
        log.info("Document ID: {} successfully deleted", documentId);
        return "Document Deleted Successfully";
    }
}