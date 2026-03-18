package com.cts.grantserve.repository;

import com.cts.grantserve.entity.Audit;
import com.cts.grantserve.enums.AuditStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditRepository extends JpaRepository<Audit, Long> {
    List<Audit> findByStatus(AuditStatus status);
}
