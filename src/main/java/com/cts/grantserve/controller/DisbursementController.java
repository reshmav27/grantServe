package com.cts.grantserve.controller;

import com.cts.grantserve.DTO.DisbursementDto;
import com.cts.grantserve.entity.Disbursement;
import com.cts.grantserve.service.IDisbursementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disbursements")
@CrossOrigin(origins = "*")
public class DisbursementController {

    @Autowired
    private IDisbursementService disbursementService;

    @PostMapping("/initiate")
    public ResponseEntity<Disbursement> initiate(@Valid @RequestBody DisbursementDto dto) {
        Disbursement saved = disbursementService.initiateDisbursement(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/application/{appId}")
    public ResponseEntity<List<Disbursement>> getByApplication(@PathVariable Long appId) {
        return ResponseEntity.ok(disbursementService.trackByApplication(appId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Disbursement>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(disbursementService.trackByStatus(status));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        disbursementService.deleteDisbursement(id);
        return ResponseEntity.ok("Disbursement deleted successfully");
    }
}