package com.cts.grantserve.dao;

import com.cts.grantserve.entity.ResearcherDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResearcherDocumentRepository extends JpaRepository<ResearcherDocument, Long> {

    // Find all documents belonging to a specific Researcher ID
    List<ResearcherDocument> findByResearcher_ResearcherID(Long researcherID);



    // Find documents by verification status
    List<ResearcherDocument> findByVerificationStatus(String status);
}