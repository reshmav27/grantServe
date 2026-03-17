package com.cts.grantserve.controller;

import com.cts.grantserve.entity.ResearcherDocument;
import com.cts.grantserve.exception.ResearcherDocumentException;
import com.cts.grantserve.service.ResearcherDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
public class ResearcherDocumentController {

    @Autowired
    ResearcherDocumentService researcherDocumentService;

    // 1. Upload a new document
    @PostMapping("/upload")
    public String upload(@RequestBody ResearcherDocument document) throws ResearcherDocumentException {
        return researcherDocumentService.uploadDocument(document);
    }

    // 2. Get document details by ID
    @GetMapping("/{id}")
    public Optional<ResearcherDocument> getDocument(@PathVariable Long id) {
        return researcherDocumentService.getDocument(id);
    }

    // 3. Delete a document
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws ResearcherDocumentException {
        return researcherDocumentService.deleteDocument(id);
    }
}
