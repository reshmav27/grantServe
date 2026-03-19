package com.cts.grantserve.controller;

import com.cts.grantserve.dto.ProgramDto;
import com.cts.grantserve.entity.Program;
import com.cts.grantserve.enums.ProgramStatus;
import com.cts.grantserve.service.IProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programs")
public class ProgramController {

    @Autowired
    private IProgramService programService;

    // Create a new program (Rollback occurs if budget initialization fails)
    @PostMapping("/createProgram")
    public ResponseEntity<String> createProgram(@RequestBody ProgramDto programDto) {
        String response = programService.createProgram(programDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get all programs
    @GetMapping
    public ResponseEntity<List<Program>> getAllPrograms() {
        return ResponseEntity.ok(programService.getAllPrograms());
    }

    // Get a specific program by ID
    @GetMapping("/{id}")
    public ResponseEntity<Program> getProgram(@PathVariable Long id) {
        return programService.getProgram(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update program details (only works if program is in DRAFT)
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProgram(@PathVariable Long id, @RequestBody ProgramDto programDto) {
        String response = programService.updateProgram(id, programDto);
        return ResponseEntity.ok(response);
    }

    // Specialized status update (e.g., DRAFT to ACTIVE)
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam ProgramStatus status) {
        String response = programService.updateProgramStatus(id, status);
        return ResponseEntity.ok(response);
    }

    // Close an active program
    @PatchMapping("/{id}/close")
    public ResponseEntity<String> closeProgram(@PathVariable Long id) {
        String response = programService.updateProgramStatus(id, ProgramStatus.CLOSED);
        return ResponseEntity.ok(response);
    }
}