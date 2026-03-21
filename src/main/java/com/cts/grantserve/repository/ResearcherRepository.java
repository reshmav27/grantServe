package com.cts.grantserve.repository;

import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.projection.IResearcherProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResearcherRepository extends JpaRepository<Researcher, Long> {

    Optional<IResearcherProjection> findResearcherByResearcherID(Long researcherID);

    List<Researcher> findByInstitution(String institution);


    List<Researcher> findByStatus(String status);
}