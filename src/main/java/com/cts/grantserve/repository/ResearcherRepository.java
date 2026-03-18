package com.cts.grantserve.repository;

import com.cts.grantserve.entity.Researcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResearcherRepository extends JpaRepository<Researcher, Long> {


    List<Researcher> findByInstitution(String institution);


    List<Researcher> findByStatus(String status);
}