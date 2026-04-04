package com.cts.grantserve.controller;

import com.cts.grantserve.dto.AllocationDto;
import com.cts.grantserve.service.IAllocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/allocate")
public class AllocationController {

    @Autowired
    private IAllocationService iAllocationService;

    @PostMapping("/initiate")
    public ResponseEntity<String> initiateAllocation(@Valid @RequestBody AllocationDto allocationDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iAllocationService.createAllocation(allocationDto));
    }
}