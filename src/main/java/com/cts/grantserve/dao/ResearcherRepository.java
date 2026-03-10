package com.cts.grantserve.dao;

import com.cts.grantserve.entity.Researcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResearcherRepository extends JpaRepository<Researcher, Long> {

    // You can add custom query methods here if needed:

    // Find researchers by institution
    List<Researcher> findByInstitution(String institution);

    // Find researchers by status
    List<Researcher> findByStatus(String status);
}
