package com.cts.grantserve.service;

import com.cts.grantserve.repository.EvaluationRepository;
import com.cts.grantserve.repository.IGrantApplicationRepository;
import com.cts.grantserve.dto.EvaluationDto;
import com.cts.grantserve.entity.Evaluation;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.exception.EvaluationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EvaluationServiceImpl implements IEvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private IGrantApplicationRepository applicationRepository;

    @Override
    public String createEvaluation(EvaluationDto evaluationDto) {
        Evaluation eval = new Evaluation();
        GrantApplication app = applicationRepository.findById(evaluationDto.applicationID())
                .orElseThrow(() -> new RuntimeException("Application not found"));
        eval.setApplication(app);
        eval.setResult(evaluationDto.result());
        eval.setDate(evaluationDto.date());
        eval.setNotes(evaluationDto.notes());
        evaluationRepository.save(eval);
        return "Evaluation submitted successfully";
    }

    @Override
    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    @Override
    public Evaluation getEvaluationById(long id) {
        return evaluationRepository.findById(id)
                .orElseThrow(() -> new EvaluationNotFoundException("Evaluation ID " + id + " not found"));
    }

    @Override
    public String deleteEvaluation(long id) {
        Evaluation eval = getEvaluationById(id);
        evaluationRepository.delete(eval);
        return "Evaluation record removed";
    }
}