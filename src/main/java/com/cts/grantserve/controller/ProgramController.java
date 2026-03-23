package com.cts.grantserve.controller;

import com.cts.grantserve.dto.ProgramDto;
import com.cts.grantserve.entity.Program;
import com.cts.grantserve.service.IProgramService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/programs")
@Slf4j
public class ProgramController {

    @Autowired
    private IProgramService programService;

    // Create a new program
    @PostMapping("/createProgram")
    public ResponseEntity<ProgramDto> createProgram(@Valid @RequestBody ProgramDto programDto) {
        log.info("Received request to create a new program: {}", programDto.title());
        ProgramDto response = programService.createProgram(programDto);
        log.info("Successfully created program with Title: {}", response.title());
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
    @PutMapping("/update")
    public ResponseEntity<String> updateProgram(@Valid @RequestBody ProgramDto programDto) {
        String response = programService.updateProgram(programDto);
        return ResponseEntity.ok(response);
    }

    // Close an active program
    @PatchMapping("/{id}/close")
    public ResponseEntity<String> closeProgram(@PathVariable Long id) {
        String response = programService.updateProgramStatusToClosed(id);
        return ResponseEntity.ok(response);
    }
}