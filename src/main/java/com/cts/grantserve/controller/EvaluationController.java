package com.cts.grantserve.controller;

import com.cts.grantserve.entity.Evaluation;
import com.cts.grantserve.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController {

    @Autowired
    EvaluationService evaluationService;

    @PostMapping("/submitEvaluation")
    public ResponseEntity<String> submitEvaluation(@RequestBody Evaluation evaluation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(evaluationService.createEvaluation(evaluation));
    }
}