package com.cts.grantserve.repository;

import com.cts.grantserve.entity.ComplianceRecord;
import com.cts.grantserve.enums.ComplianceResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplianceRecordRepository extends JpaRepository<ComplianceRecord, Long> {
    List<ComplianceRecord> findByResult(ComplianceResult result);
}
