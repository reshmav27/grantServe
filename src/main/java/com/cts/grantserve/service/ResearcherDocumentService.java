package com.cts.grantserve.service;

import com.cts.grantserve.entity.ResearcherDocument;
import com.cts.grantserve.dao.ResearcherDocumentRepository;
import com.cts.grantserve.exception.ResearcherDocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ResearcherDocumentService {

    @Autowired
    ResearcherDocumentRepository researcherDocumentRepository;

    public String uploadDocument(ResearcherDocument document) throws ResearcherDocumentException {
        document.setUploadedDate(LocalDateTime.now()); // Set current time
        document.setVerificationStatus("Pending"); // Initial status
        researcherDocumentRepository.save(document);
        return "Document Uploaded Successfully";
    }

    public String deleteDocument(Long id) throws ResearcherDocumentException {
        researcherDocumentRepository.deleteById(id);
        return "Document Deleted Successfully";
    }

    public Optional<ResearcherDocument> getDocument(Long id) {
        return researcherDocumentRepository.findById(id);
    }
}
