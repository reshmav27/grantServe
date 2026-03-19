package com.cts.grantserve.service;

import com.cts.grantserve.dto.ResearcherDocumentDto;
import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.entity.ResearcherDocument;
import com.cts.grantserve.repository.ResearcherDocumentRepository;
import com.cts.grantserve.repository.ResearcherRepository;
import com.cts.grantserve.exception.ResearcherDocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ResearcherDocumentService {

    @Autowired
    private ResearcherDocumentRepository researcherDocumentRepository;

    @Autowired
    private ResearcherRepository researcherRepository;

    public String uploadDocument(ResearcherDocumentDto documentDto) throws ResearcherDocumentException {
        // 1. Find the Researcher entity to establish the relationship
        Researcher researcher = researcherRepository.findById(documentDto.researcherID())
                .orElseThrow(() -> new ResearcherDocumentException("Researcher not found with ID: " + documentDto.researcherID()));

        // 2. Manually map DTO to Entity
        ResearcherDocument document = new ResearcherDocument();
        document.setResearcher(researcher); // Linking the Foreign Key
        document.setDocType(documentDto.docType());
        document.setFileURI(documentDto.fileURI());

        // 3. Set system-generated fields
        document.setUploadedDate(LocalDateTime.now());
        document.setVerificationStatus("Pending");

        researcherDocumentRepository.save(document);
        return "Document Uploaded Successfully";
    }

    public String deleteDocument(Long id) throws ResearcherDocumentException {
        if (!researcherDocumentRepository.existsById(id)) {
            throw new ResearcherDocumentException("Document ID " + id + " does not exist.");
        }
        researcherDocumentRepository.deleteById(id);
        return "Document Deleted Successfully";
    }

    public Optional<ResearcherDocument> getDocument(Long id) {
        return researcherDocumentRepository.findById(id);
    }
}