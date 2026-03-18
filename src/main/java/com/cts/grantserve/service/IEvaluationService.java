package com.cts.grantserve.service;

import com.cts.grantserve.dto.EvaluationDto;
import com.cts.grantserve.entity.Evaluation;
import java.util.List;

public interface IEvaluationService {
    String createEvaluation(EvaluationDto evaluationDto);
    List<Evaluation> getAllEvaluations();
    Evaluation getEvaluationById(long id);
    String deleteEvaluation(long id);
}