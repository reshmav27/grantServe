package com.cts.grantserve.controller;

import com.cts.grantserve.DTO.AuditDto;
import com.cts.grantserve.entity.Audit;
import com.cts.grantserve.enums.AuditStatus;
import com.cts.grantserve.service.AuditService;
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
    private AuditService auditService;
    @PostMapping("/createAudit")
    public ResponseEntity<String> createAudit(@Valid @RequestBody AuditDto audit){
        return ResponseEntity.status(HttpStatus.CREATED).body(auditService.createAudit(audit));
    }
    @DeleteMapping("/DeleteAudit/{id}")
    public ResponseEntity<String> DeleteAudit(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(auditService.DeleteAudit(id));

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