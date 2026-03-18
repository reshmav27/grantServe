package com.cts.grantserve.controller;

import com.cts.grantserve.DTO.EvaluationDto;
import com.cts.grantserve.entity.Evaluation;
import com.cts.grantserve.service.IEvaluationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController {

    @Autowired
    private IEvaluationService evaluationService;

    @PostMapping("/submit")
    public ResponseEntity<String> submit(@Valid @RequestBody EvaluationDto evaluationDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(evaluationService.createEvaluation(evaluationDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Evaluation>> getAll() {
        return ResponseEntity.ok(evaluationService.getAllEvaluations());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        return ResponseEntity.ok(evaluationService.deleteEvaluation(id));
    }
}