package com.cts.grantserve.service;

import com.cts.grantserve.dto.ResearcherDocumentDto;
import com.cts.grantserve.entity.ResearcherDocument;
import com.cts.grantserve.exception.ResearcherDocumentException;
import java.util.Optional;

public interface IResearcherDocumentService {
    String uploadDocument(ResearcherDocumentDto documentDto) throws ResearcherDocumentException;
    Optional<ResearcherDocument> getDocument(Long id);
    String deleteDocument(Long id) throws ResearcherDocumentException;
}