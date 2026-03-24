package com.cts.grantserve.controller;

import com.cts.grantserve.dto.ResearcherDocumentDto;
import com.cts.grantserve.entity.ResearcherDocument;
import com.cts.grantserve.exception.ResearcherDocumentException;
import com.cts.grantserve.service.IResearcherDocumentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j; // 1. Import Slf4j
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/documents")
public class ResearcherDocumentController {

    @Autowired
    private IResearcherDocumentService researcherDocumentService;

    @GetMapping("/all")
    public List<ResearcherDocument> getAllDocuments() {
        log.info("REST request to retrieve all documents");
        List<ResearcherDocument> docs = researcherDocumentService.getAllDocuments();
        log.debug("Total documents retrieved: {}", docs.size());
        return docs;
    }

    @PostMapping("/upload")
    public String upload(@Valid @RequestBody ResearcherDocumentDto documentDto) throws ResearcherDocumentException {
        log.info("REST request to upload document for Researcher ID: {}", documentDto.researcherID());
        String response = researcherDocumentService.uploadDocument(documentDto);
        log.info("Document upload status: {}", response);
        return response;
    }

    @GetMapping("/{id}")
    public Optional<ResearcherDocument> getDocument(@PathVariable Long id) {
        log.info("REST request to fetch document details for ID: {}", id);
        return researcherDocumentService.getDocument(id);
    }

    @DeleteMapping("/delete/{researcherId}/{documentId}")
    public String delete(
            @PathVariable Long researcherId,
            @PathVariable Long documentId) throws ResearcherDocumentException {

        log.warn("REST request to DELETE Document ID: {} belonging to Researcher ID: {}", documentId, researcherId);
        String response = researcherDocumentService.deleteDocument(researcherId, documentId);
        log.info("Successfully processed deletion for Document ID: {}", documentId);
        return response;
    }
}