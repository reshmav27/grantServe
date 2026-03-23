package com.cts.grantserve.service;

import com.cts.grantserve.dto.ResearcherDocumentDto;
import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.entity.ResearcherDocument;
import com.cts.grantserve.exception.ResearcherDocumentException;
import com.cts.grantserve.repository.ResearcherDocumentRepository;
import com.cts.grantserve.repository.ResearcherRepository; // Import this
import com.cts.grantserve.util.ClassUtilSeparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ResearcherDocumentServiceImpl implements IResearcherDocumentService {

    @Autowired
    private ResearcherDocumentRepository researcherDocumentRepository;

    @Autowired
    private ResearcherRepository researcherRepository; // Injected to link the researcher

    @Override
    public String uploadDocument(ResearcherDocumentDto documentDto) throws ResearcherDocumentException {
        // 1. Convert DTO to Entity using your utility
        ResearcherDocument doc = ClassUtilSeparator.documentUploadUtil(documentDto);

        // 2. Look up the Researcher by ID sent in the DTO
        // Make sure your ResearcherDocumentDto has a getResearcherID() method
        Researcher researcher = researcherRepository.findById(documentDto.researcherID())
                .orElseThrow(() -> new ResearcherDocumentException(
                        "Cannot upload document. Researcher not found with ID: " + documentDto.researcherID(),
                        HttpStatus.NOT_FOUND));

        // 3. Link the Researcher to the Document (This fills the 'researcher_id' column)
        doc.setResearcher(researcher);

        // 4. Set the auto-generated metadata
        doc.setUploadedDate(LocalDateTime.now());
        doc.setVerificationStatus("Pending");

        // 5. Save the document
        researcherDocumentRepository.save(doc);

        return "Document Uploaded Successfully";
    }

    @Override
    public Optional<ResearcherDocument> getDocument(Long id) {
        // Because of your @JsonIgnoreProperties("documents") fix in the Entity,
        // this will now return the Document AND the Researcher details in Postman.
        return researcherDocumentRepository.findById(id);
    }

    @Override
    public List<ResearcherDocument> getAllDocuments() {
        return researcherDocumentRepository.findAll();
    }

    @Override
    public String deleteDocument(Long researcherId, Long documentId) throws ResearcherDocumentException {
        ResearcherDocument doc = researcherDocumentRepository
                .findByDocumentIDAndResearcher_ResearcherID(documentId, researcherId) // Match the new name here
                .orElseThrow(() -> new ResearcherDocumentException(
                        "Document not found or does not belong to researcher ID: " + researcherId,
                        HttpStatus.NOT_FOUND));

        researcherDocumentRepository.delete(doc);
        return "Document Deleted Successfully";
    }
}