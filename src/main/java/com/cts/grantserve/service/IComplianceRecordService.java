package com.cts.grantserve.service;

import com.cts.grantserve.dto.ComplianceRecordDto;
import com.cts.grantserve.entity.ComplianceRecord;
import com.cts.grantserve.enums.ComplianceResult;
import com.cts.grantserve.exception.ComplianceRecordException;

import java.util.List;
import java.util.Optional;

public interface IComplianceRecordService {

    String createComplianceRecord(ComplianceRecordDto complianceRecordDto) throws ComplianceRecordException;

    String deleteComplianceRecord(int id) throws ComplianceRecordException;

    Optional<ComplianceRecord> getComplianceRecord(int id);

    List<ComplianceRecord> getComplianceRecordByResult(ComplianceResult result);

    Optional<ComplianceRecord> updateComplianceRecordResult(int id, ComplianceResult result);

}