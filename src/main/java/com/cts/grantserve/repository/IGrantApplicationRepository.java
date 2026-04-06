package com.cts.grantserve.repository;

import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.entity.Program;
import com.cts.grantserve.entity.Researcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGrantApplicationRepository extends JpaRepository<GrantApplication,Long>, JpaSpecificationExecutor<GrantApplication> {
    Optional<List<GrantApplication>> findByResearcher_ResearcherID(Long researcherID);

    Optional<List<GrantApplication>> findByProgram_ProgramID(Long programID);

    Long countByResearcher_ResearcherID(Long researcherID);

    Optional<Researcher> findByResearcher(Long researcherID);


    Optional<Program> findByProgram(Long programID);
}
