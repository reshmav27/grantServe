package com.cts.grantserve.service;

import com.cts.grantserve.dao.EvaluationDao;
import com.cts.grantserve.entity.Evaluation;
import com.cts.grantserve.exception.EvaluationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {

    @Autowired
    EvaluationDao evaluationDao;

    public String createEvaluation(Evaluation evaluation) {
        evaluationDao.save(evaluation);
       return "created successfully";
    }
    public Evaluation getEvaluationById(long id) {
        return evaluationDao.findById(id)
                .orElseThrow(() -> new EvaluationNotFoundException("Evaluation not found for ID: " + id));
    }
}