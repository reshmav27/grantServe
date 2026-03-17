package com.cts.grantserve.service;

import com.cts.grantserve.DTO.AuditDto;
import com.cts.grantserve.DTO.GrantApplicationDto;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.exception.GrantApplicationException;
import com.cts.grantserve.repository.AuditRepository;
import com.cts.grantserve.entity.Audit;
import com.cts.grantserve.enums.AuditStatus;
import com.cts.grantserve.exception.AuditException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuditService {
    @Autowired
    AuditRepository auditRepository;
    public String createAudit(AuditDto auditDto) throws AuditException {
        Audit audit = new Audit();
        BeanUtils.copyProperties(auditDto, audit);
        auditRepository.save(audit);
        return "Created SuccessFully";
    }

    public String DeleteAudit(int id) throws  AuditException{
        auditRepository.deleteById((long) id);
        return "Deleted SuccessFully";
    }

    public Optional<Audit> getAudit(int id) {
        return auditRepository.findById((long) id);
    }

    public List<Audit> getAuditByStatus(AuditStatus status) {
        return auditRepository.findByStatus(status);
    }
}