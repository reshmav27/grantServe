package com.cts.grantserve.controller;

import com.cts.grantserve.dto.DisbursementDto;
import com.cts.grantserve.entity.Disbursement;
import com.cts.grantserve.service.IDisbursementService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disbursements")
@CrossOrigin(origins = "*")
@Slf4j
public class DisbursementController {

    @Autowired
    private IDisbursementService disbursementService;

    @PostMapping("/initiate")
    public ResponseEntity<String> initiate(@Valid @RequestBody DisbursementDto dto) {
        log.info("REST Request: Initiating new disbursement for Application ID: {}", dto.applicationID());

        Disbursement saved = disbursementService.initiateDisbursement(dto);

        log.info("REST Success: Disbursement record created with ID: {}", saved.getDisbursementID());
        return new ResponseEntity<>("Disbursement initiated successfully", HttpStatus.CREATED);
    }

    @GetMapping("/application/{appId}")
    public ResponseEntity<List<Disbursement>> getByApplication(@PathVariable Long appId) {
        log.info("REST Request: Tracking disbursements for Application ID: {}", appId);

        List<List<Disbursement>> list = List.of(disbursementService.trackByApplication(appId));
        log.info("REST Response: Found {} records for Application ID: {}", list.size(), appId);

        return ResponseEntity.ok(disbursementService.trackByApplication(appId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Disbursement>> getByStatus(@PathVariable String status) {
        log.info("REST Request: Fetching all disbursements with status: {}", status);

        List<Disbursement> disbursements = disbursementService.trackByStatus(status);
        log.info("REST Response: Found {} records with status: {}", disbursements.size(), status);

        return ResponseEntity.ok(disbursements);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("REST Request: Attempting to delete Disbursement ID: {}", id);

        disbursementService.deleteDisbursement(id);

        log.info("REST Success: Disbursement ID: {} has been deleted", id);
        return ResponseEntity.ok("Disbursement deleted successfully");
    }
}