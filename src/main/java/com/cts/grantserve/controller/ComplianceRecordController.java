package com.cts.grantserve.controller;

import com.cts.grantserve.dto.ComplianceRecordDto;
import com.cts.grantserve.entity.Audit;
import com.cts.grantserve.entity.ComplianceRecord;
import com.cts.grantserve.enums.ComplianceResult;
import com.cts.grantserve.service.ComplianceRecordServiceImpl;
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
public class ComplianceRecordController {

    @Autowired
    ComplianceRecordServiceImpl complianceRecordService;

    @PostMapping("/createComplianceRecord")
    public ResponseEntity<String> createComplianceRecord(@Valid @RequestBody ComplianceRecordDto complianceRecord){
        return ResponseEntity.status(HttpStatus.CREATED).body(complianceRecordService.createComplianceRecord(complianceRecord));
    }

    @DeleteMapping("/DeleteComplianceRecord/{id}")
    public ResponseEntity<String> DeleteComplianceRecord(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(complianceRecordService.deleteComplianceRecord(id));
    }

    @GetMapping("/complianceRecords")
    public ResponseEntity<?> getAllComplianceRecord() {
        return ResponseEntity.ok(complianceRecordService.getAllComplianceRecord());
    }

    @GetMapping("getComplianceRecord/{id}")
    public ResponseEntity<ComplianceRecord> getComplianceRecord(@PathVariable int id) {
        return complianceRecordService.getComplianceRecord(id)
                .map(app -> ResponseEntity.ok(app))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("getComplianceRecordByResult/{result}")
    public ResponseEntity<?> getComplianceRecordByResult(@PathVariable ComplianceResult result) {
        ComplianceResult enumValue = toComplianceResult(String.valueOf(result));
        return ResponseEntity.ok(complianceRecordService.getComplianceRecordByResult(enumValue));
    }

    @PatchMapping("/updateComplianceRecordResult/{id}")
    public ResponseEntity<ComplianceRecord> updateComplianceRecordResult(
            @PathVariable int id,
            @Valid @RequestBody ComplianceRecord complianceRecord
    ) {
        return complianceRecordService.updateComplianceRecordResult(id, complianceRecord.getResult())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compliance Record not found with id " + id));
    }

    private ComplianceResult toComplianceResult(String value) {
        try {
            return ComplianceResult.valueOf(value.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid result. Allowed values: " + Arrays.toString(ComplianceResult.values())
            );
        }
    }
}