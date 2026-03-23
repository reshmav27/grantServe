package com.cts.grantserve.controller;

import com.cts.grantserve.dto.ResearcherDocumentDto;
import com.cts.grantserve.entity.ResearcherDocument;
import com.cts.grantserve.exception.ResearcherDocumentException;
import com.cts.grantserve.service.IResearcherDocumentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
public class ResearcherDocumentController {

    @Autowired
    private IResearcherDocumentService researcherDocumentService; // Use the Interface

    // Add this to your existing ResearcherDocumentController
    @GetMapping("/all")
    public List<ResearcherDocument> getAllDocuments() {
        return researcherDocumentService.getAllDocuments();
    }

    @PostMapping("/upload")
    public String upload(@Valid @RequestBody ResearcherDocumentDto documentDto) throws ResearcherDocumentException {
        return researcherDocumentService.uploadDocument(documentDto);
    }

    @GetMapping("/{id}")
    public Optional<ResearcherDocument> getDocument(@PathVariable Long id) {
        return researcherDocumentService.getDocument(id);
    }

    @DeleteMapping("/delete/{researcherId}/{documentId}")
    public String delete(
            @PathVariable Long researcherId,
            @PathVariable Long documentId) throws ResearcherDocumentException {
        return researcherDocumentService.deleteDocument(researcherId, documentId);
    }
}