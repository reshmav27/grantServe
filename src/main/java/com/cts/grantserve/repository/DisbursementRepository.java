package com.cts.grantserve.repository;

import com.cts.grantserve.entity.Disbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisbursementRepository extends JpaRepository<Disbursement, Long> {

    List<Disbursement> findByApplication_ApplicationID(Long applicationID);

    List<Disbursement> findByStatus(String status);
}

