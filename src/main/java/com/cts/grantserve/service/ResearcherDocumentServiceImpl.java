package com.cts.grantserve.service;

import com.cts.grantserve.dto.ResearcherDocumentDto;
import com.cts.grantserve.entity.ResearcherDocument;
import com.cts.grantserve.exception.ResearcherDocumentException;
import com.cts.grantserve.repository.ResearcherDocumentRepository;
import com.cts.grantserve.util.ClassUtilSeparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ResearcherDocumentServiceImpl implements IResearcherDocumentService {

    @Autowired
    private ResearcherDocumentRepository researcherDocumentRepository;

    @Override
    public String uploadDocument(ResearcherDocumentDto documentDto) throws ResearcherDocumentException {
        ResearcherDocument doc = ClassUtilSeparator.documentUploadUtil(documentDto);
        doc.setUploadedDate(LocalDateTime.now());
        doc.setVerificationStatus("Pending");
        researcherDocumentRepository.save(doc);
        return "Document Uploaded Successfully";
    }

    @Override
    public Optional<ResearcherDocument> getDocument(Long id) {
        return researcherDocumentRepository.findById(id);
    }

    @Override
    public String deleteDocument(Long id) throws ResearcherDocumentException {
        if (!researcherDocumentRepository.existsById(id)) {
            throw new ResearcherDocumentException("Document not found: " + id, HttpStatus.NOT_FOUND);
        }
        researcherDocumentRepository.deleteById(id);
        return "Document Deleted Successfully";
    }
}