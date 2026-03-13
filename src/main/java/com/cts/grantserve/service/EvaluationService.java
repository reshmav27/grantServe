package com.cts.grantserve.service;

import com.cts.grantserve.Repository.EvaluationRepository;
import com.cts.grantserve.entity.Evaluation;
import com.cts.grantserve.exception.EvaluationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {

    @Autowired
    EvaluationRepository evaluationRepository;

    public String createEvaluation(Evaluation evaluation) {
        evaluationRepository.save(evaluation);
       return "created successfully";
    }
    public Evaluation getEvaluationById(long id) {
        return evaluationRepository.findById(id)
                .orElseThrow(() -> new EvaluationNotFoundException("Evaluation not found for ID: " + id));
    }
}