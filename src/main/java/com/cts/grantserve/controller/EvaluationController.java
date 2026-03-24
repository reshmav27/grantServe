package com.cts.grantserve.controller;

import com.cts.grantserve.dto.EvaluationDto;
import com.cts.grantserve.entity.Evaluation;
import com.cts.grantserve.service.IEvaluationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/evaluation")
public class EvaluationController {

    @Autowired
    private IEvaluationService evaluationService;

    @PostMapping("/submit")
    public ResponseEntity<String> submit(@Valid @RequestBody EvaluationDto evaluationDto) {
        // Record accessor: applicationID() instead of getApplicationID()
        log.info("Request: Submit evaluation for Application ID: {}", evaluationDto.applicationID());
        String response = evaluationService.createEvaluation(evaluationDto);
        log.info("Success: Evaluation created for Application ID: {}", evaluationDto.applicationID());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Evaluation>> getAll() {
        log.info("Request: Fetching all evaluations");
        List<Evaluation> evaluations = evaluationService.getAllEvaluations();
        log.info("Success: Retrieved {} records", evaluations.size());
        return ResponseEntity.ok(evaluations);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        log.warn("Request: Deleting evaluation ID: {}", id);
        String response = evaluationService.deleteEvaluation(id);
        log.info("Success: Evaluation ID: {} deleted", id);
        return ResponseEntity.ok(response);
    }
}