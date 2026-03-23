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
import java.util.Locale;

@RestController
@RequestMapping("/GrantServe")
public class AuditController {
    @Autowired
    private AuditServiceImpl auditService;
    @PostMapping("/createAudit")
    public ResponseEntity<String> createAudit(@Valid @RequestBody AuditDto audit){
        return ResponseEntity.status(HttpStatus.CREATED).body(auditService.createAudit(audit));
    }
    @DeleteMapping("/deleteAudit/{id}")
    public ResponseEntity<String> DeleteAudit(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(auditService.deleteAudit(id));

    }
    @GetMapping("getAudit/{id}")
    public ResponseEntity<Audit> getAudit(@PathVariable int id) {
        return auditService.getAudit(id)
                .map(app -> ResponseEntity.ok(app))
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("getAuditByStatus/{status}")
    public ResponseEntity<?> getAuditByStatus(@PathVariable AuditStatus status) {
        AuditStatus enumValue = toAuditStatus(String.valueOf(status));
        return ResponseEntity.ok(auditService.getAuditByStatus(enumValue));
    }

    @PatchMapping("/updateStatus/{id}")
    public ResponseEntity<Audit> updateAuditStatus(
            @PathVariable int id,
            @Valid @RequestBody Audit audit
    ) {
        return auditService.updateAuditStatus(id, audit.getStatus())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Audit not found with id " + id));
    }

    private AuditStatus toAuditStatus(String value) {
        try {
            return AuditStatus.valueOf(value.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid status. Allowed values: " + Arrays.toString(AuditStatus.values())
            );
        }
    }
}