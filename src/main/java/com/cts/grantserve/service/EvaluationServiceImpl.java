package com.cts.grantserve.service;

import com.cts.grantserve.dto.EvaluationDto;
import com.cts.grantserve.entity.Evaluation;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.exception.EvaluationNotFoundException;
import com.cts.grantserve.repository.EvaluationRepository;
import com.cts.grantserve.repository.IGrantApplicationRepository;
import com.cts.grantserve.util.ClassUtilSeparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class EvaluationServiceImpl implements IEvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private IGrantApplicationRepository applicationRepository;

    @Override
    @Transactional
    public String createEvaluation(EvaluationDto evaluationDto) {
        log.info("Service: Creating Evaluation for Application ID: {}", evaluationDto.applicationID());

        // 1. Fetch the Grant Application from the database
        GrantApplication app = applicationRepository.findById(evaluationDto.applicationID())
                .orElseThrow(() -> {
                    log.error("Service Error: Application ID {} not found", evaluationDto.applicationID());
                    return new RuntimeException("Application not found");
                });

        // 2. Use the Util class to create the Evaluation entity
        Evaluation eval = ClassUtilSeparator.evaluationUtil(evaluationDto, app);

        // 3. Update the Status in the Grant Application Table
        // This is the core requirement: changing the application status based on the evaluation result
        String statusResult = evaluationDto.result().toString();
        app.setStatus(statusResult);
        log.info("Application ID {} status updated to: {}", app.getApplicationID(), statusResult);

        // 4. Save the updated Grant Application (Updates the 'status' column in your DB)
        applicationRepository.save(app);

        // 5. Save the Evaluation record
        evaluationRepository.save(eval);

        return "Evaluation processed. Grant Application status updated to " + statusResult;
    }

    @Override
    public List<Evaluation> getAllEvaluations() {
        log.info("Service: Fetching all evaluation records");
        return evaluationRepository.findAll();
    }

    @Override
    public Evaluation getEvaluationById(long id) {
        log.info("Service: Searching for evaluation ID: {}", id);
        return evaluationRepository.findById(id)
                .orElseThrow(() -> new EvaluationNotFoundException("Evaluation ID " + id + " not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public String deleteEvaluation(long id) {
        log.info("Service: Deleting evaluation ID: {}", id);
        Evaluation eval = getEvaluationById(id);
        evaluationRepository.delete(eval);
        return "Evaluation record removed successfully";
    }
}