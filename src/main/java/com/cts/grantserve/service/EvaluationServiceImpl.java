package com.cts.grantserve.service;

import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.entity.ResearcherDocument;
import com.cts.grantserve.repository.EvaluationRepository;
import com.cts.grantserve.repository.IGrantApplicationRepository;
import com.cts.grantserve.dto.EvaluationDto;
import com.cts.grantserve.entity.Evaluation;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.exception.EvaluationNotFoundException;
import com.cts.grantserve.repository.ResearcherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class EvaluationServiceImpl implements IEvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private ResearcherRepository researcherRepository; // Add this instance variable

    @Autowired
    private IGrantApplicationRepository applicationRepository;

    @Override
    public String createEvaluation(EvaluationDto evaluationDto) {
        log.info("Service: Processing evaluation creation for Application ID: {}", evaluationDto.applicationID());

        Evaluation eval = new Evaluation();
        //ResearcherDocument researcherDocument=new ResearcherDocument();
        GrantApplication app = applicationRepository.findById(evaluationDto.applicationID())
                .orElseThrow(() -> {
                    log.error("Service Error: Application ID {} not found", evaluationDto.applicationID());
                    return new RuntimeException("Application not found");
                });

        eval.setApplication(app);
        eval.setResult(evaluationDto.result());
        eval.setDate(evaluationDto.date());
        eval.setNotes(evaluationDto.notes());


        Researcher researcher = app.getResearcher();
        if (researcher != null && researcher.getDocuments() != null) {
            String finalStatus = evaluationDto.result().toString(); // "APPROVED" or "REJECTED"

            for (ResearcherDocument doc : researcher.getDocuments()) {
                doc.setVerificationStatus(finalStatus);
                log.info("Updating document ID {} to status: {}", doc.getDocumentID(), finalStatus);
            }
             //Save the researcher to persist document changes (if cascading is on)
             researcherRepository.save(researcher);

        }
        evaluationRepository.save(eval);
        log.info("Service: Evaluation saved successfully in database");
        return "Evaluation submitted successfully";
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
                .orElseThrow(() -> {
                    log.error("Service Error: Evaluation ID {} not found", id);
                    return new EvaluationNotFoundException("Evaluation ID " + id + " not found");
                });
    }

    @Override
    public String deleteEvaluation(long id) {
        log.info("Service: Attempting to delete evaluation ID: {}", id);
        Evaluation eval = getEvaluationById(id);
        evaluationRepository.delete(eval);
        log.info("Service: Evaluation record ID {} successfully removed", id);
        return "Evaluation record removed";
    }
}