package com.cts.grantserve.service;

import com.cts.grantserve.dto.AuditDto;
import com.cts.grantserve.repository.AuditRepository;
import com.cts.grantserve.entity.Audit;
import com.cts.grantserve.enums.AuditStatus;
import com.cts.grantserve.exception.AuditException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.rmi.server.LogStream.log;

@Slf4j
@Service
public class AuditServiceImpl implements IAuditService{
    @Autowired
    AuditRepository auditRepository;

    @Override
    public String createAudit(AuditDto auditDto) throws AuditException {
        Audit audit = new Audit();
        BeanUtils.copyProperties(auditDto, audit);
        auditRepository.save(audit);
        log.info("Audit is created Successfully");
        return "Created SuccessFully";
    }

    @Override
    public String deleteAudit(int id) throws  AuditException{
        auditRepository.deleteById((long) id);
        return "Deleted SuccessFully";
    }

    @Override
    public Optional<Audit> getAudit(int id) {
        return auditRepository.findById((long) id);
    }

    @Override
    public List<Audit> getAuditByStatus(AuditStatus status) {
        return auditRepository.findByStatus(status);
    }

    @Override
    public Optional<Audit> updateAuditStatus(int id, AuditStatus status) {
        return auditRepository.findById((long)id).map(audit -> {
            audit.setStatus(status);
            return auditRepository.save(audit);
        });
    }
}