package com.cts.grantserve.service;

import com.cts.grantserve.dto.AuditDto;
import com.cts.grantserve.entity.Audit;
import com.cts.grantserve.enums.AuditStatus;
import com.cts.grantserve.exception.AuditException;
import com.cts.grantserve.repository.AuditRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AuditServiceImpl implements IAuditService {

    @Autowired
    private AuditRepository auditRepository;

    @Override
    public String createAudit(AuditDto auditDto) throws AuditException {
        try {
            Audit audit = new Audit();
            BeanUtils.copyProperties(auditDto, audit);
            auditRepository.save(audit);
            log.info("Audit created successfully for officer ID: {}", audit.getOfficerID());
            return "Created Successfully";
        } catch (Exception e) {
            throw new AuditException("Failed to create audit: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Audit> getAllAudits() {
        return auditRepository.findAll();
    }

    @Override
    public String updateAuditStatus(int id, AuditStatus status) throws AuditException {
        // Casting int to Long for JpaRepository findById
        Audit audit = auditRepository.findById((long) id)
                .orElseThrow(() -> new AuditException("Audit not found with id: " + id, HttpStatus.NOT_FOUND));

        audit.setStatus(status);
        auditRepository.save(audit);

        log.info("Audit status updated to {} for ID: {}", status, id);
        return "Audit status updated successfully";
    }

    @Override
    public String deleteAudit(int id) throws AuditException {
        if (!auditRepository.existsById((long) id)) {
            throw new AuditException("Audit not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        auditRepository.deleteById((long) id);
        log.info("Audit with ID: {} deleted successfully", id);
        return "Deleted Successfully";
    }

    @Override
    public Optional<Audit> getAudit(int id) {
        return auditRepository.findById((long) id);
    }

    @Override
    public List<Audit> getAuditByStatus(AuditStatus status) {
        return auditRepository.findByStatus(status);
    }
}