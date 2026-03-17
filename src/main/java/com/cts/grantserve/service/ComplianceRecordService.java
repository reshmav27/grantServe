package com.cts.grantserve.service;

import com.cts.grantserve.DTO.AuditDto;
import com.cts.grantserve.DTO.ComplianceRecordDto;
import com.cts.grantserve.entity.Audit;
import com.cts.grantserve.exception.AuditException;
import com.cts.grantserve.repository.ComplianceRecordRepository;
import com.cts.grantserve.entity.ComplianceRecord;
import com.cts.grantserve.enums.ComplianceResult;
import com.cts.grantserve.exception.ComplianceRecordException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplianceRecordService {
    @Autowired
    ComplianceRecordRepository complianceRecordRepository;
    public String createComplianceRecord(ComplianceRecordDto complianceRecordDto) throws ComplianceRecordException {
        ComplianceRecord complianceRecord = new ComplianceRecord();
        BeanUtils.copyProperties(complianceRecordDto, complianceRecord);
        complianceRecordRepository.save(complianceRecord);
        return "Created SuccessFully";
    }

    public String DeleteComplianceRecord(int id) throws  ComplianceRecordException{
        complianceRecordRepository.deleteById((long) id);
        return "Deleted SuccessFully";
    }

    public Optional<ComplianceRecord> getComplianceRecord(int id) {
        return complianceRecordRepository.findById((long) id);
    }

    public List<ComplianceRecord> getComplianceRecordByResult(ComplianceResult result) {
        return complianceRecordRepository.findByResult(result);
    }

}