package com.cts.grantserve.dao;

import com.cts.grantserve.entity.Disbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisbursementRepository extends JpaRepository<Disbursement, Long> {

    // Track disbursements for a specific application
    List<Disbursement> findByApplication_ApplicationID(Long applicationID);

    // Monitor disbursements by status (e.g., PENDING, COMPLETED) [cite: 63, 71]
    List<Disbursement> findByStatus(String status);
}