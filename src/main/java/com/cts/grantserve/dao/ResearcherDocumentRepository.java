package com.cts.grantserve.dao;

import com.cts.grantserve.entity.ResearcherDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResearcherDocumentRepository extends JpaRepository<ResearcherDocument, Long> {


    List<ResearcherDocument> findByResearcher_ResearcherID(Long researcherID);




    List<ResearcherDocument> findByVerificationStatus(String status);
}