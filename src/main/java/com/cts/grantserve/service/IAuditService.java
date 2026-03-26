package com.cts.grantserve.service;

import com.cts.grantserve.dto.AuditDto;
import com.cts.grantserve.entity.Audit;
import com.cts.grantserve.enums.AuditStatus;
import com.cts.grantserve.exception.AuditException;

import java.util.List;
import java.util.Optional;

public interface IAuditService {
    String createAudit(AuditDto auditDto) throws AuditException;
    String deleteAudit(int id) throws AuditException;
    Optional<Audit> getAudit(int id);
    List<Audit> getAuditByStatus(AuditStatus status);

    // New methods to match Controller
    List<Audit> getAllAudits();
    String updateAuditStatus(int id, AuditStatus status) throws AuditException;
}