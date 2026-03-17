package com.cts.grantserve.controller;

import com.cts.grantserve.DTO.ResearcherDocumentDto;
import com.cts.grantserve.entity.ResearcherDocument;
import com.cts.grantserve.exception.ResearcherDocumentException;
import com.cts.grantserve.service.ResearcherDocumentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
public class ResearcherDocumentController {

    @Autowired
    private ResearcherDocumentService researcherDocumentService;

    @PostMapping("/upload")
    public String upload(@Valid @RequestBody ResearcherDocumentDto documentDto) throws ResearcherDocumentException {
        return researcherDocumentService.uploadDocument(documentDto);
    }

    @GetMapping("/{id}")
    public Optional<ResearcherDocument> getDocument(@PathVariable Long id) {
        return researcherDocumentService.getDocument(id);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws ResearcherDocumentException {
        return researcherDocumentService.deleteDocument(id);
    }
}