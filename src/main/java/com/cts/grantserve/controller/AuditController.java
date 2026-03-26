package com.cts.grantserve.controller;

import com.cts.grantserve.dto.AuditDto;
import com.cts.grantserve.entity.Audit;
import com.cts.grantserve.enums.AuditStatus;
import com.cts.grantserve.service.AuditServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/GrantServe")
public class AuditController {

    @Autowired
    private AuditServiceImpl auditService;

    // 1. Fixed: Added missing endpoint for /GrantServe/audits
    @GetMapping("/audits")
    public ResponseEntity<List<Audit>> getAllAudits() {
        return ResponseEntity.ok(auditService.getAllAudits());
    }

    @PostMapping("/createAudit")
    public ResponseEntity<String> createAudit(@Valid @RequestBody AuditDto audit) {
        return ResponseEntity.status(HttpStatus.CREATED).body(auditService.createAudit(audit));
    }

    // 2. Fixed: Added missing endpoint for /GrantServe/updateAuditStatus/{id}
    // Using @PatchMapping as it's a partial update
    @PatchMapping("/updateAuditStatus/{id}")
    public ResponseEntity<String> updateAuditStatus(@PathVariable int id, @RequestParam String status) {
        AuditStatus enumStatus = toAuditStatus(status);
        return ResponseEntity.ok(auditService.updateAuditStatus(id, enumStatus));
    }

    @DeleteMapping("/deleteAudit/{id}")
    public ResponseEntity<String> deleteAudit(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(auditService.deleteAudit(id));
    }

    @GetMapping("/getAudit/{id}")
    public ResponseEntity<Audit> getAudit(@PathVariable int id) {
        return auditService.getAudit(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAuditByStatus/{status}")
    public ResponseEntity<List<Audit>> getAuditByStatus(@PathVariable String status) {
        AuditStatus enumValue = toAuditStatus(status);
        return ResponseEntity.ok(auditService.getAuditByStatus(enumValue));
    }

    /**
     * Helper method to convert String to Enum safely
     */
    private AuditStatus toAuditStatus(String value) {
        try {
            return AuditStatus.valueOf(value.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid status. Allowed values: " + Arrays.toString(AuditStatus.values())
            );
        }
    }
}