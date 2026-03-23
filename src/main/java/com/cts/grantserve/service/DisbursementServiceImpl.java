package com.cts.grantserve.service;

import com.cts.grantserve.dto.DisbursementDto;
import com.cts.grantserve.entity.Budget;
import com.cts.grantserve.entity.Disbursement;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.exception.DisbursementException;
import com.cts.grantserve.repository.BudgetRepository;
import com.cts.grantserve.repository.DisbursementRepository;
import com.cts.grantserve.repository.IGrantApplicationRepository;
import com.cts.grantserve.util.ClassUtilSeparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j // Injects the SLF4J 'log' object
public class DisbursementServiceImpl implements IDisbursementService {

    @Autowired
    private DisbursementRepository disbursementRepo;

    @Autowired
    private BudgetRepository budgetRepo;

    @Autowired
    private IGrantApplicationRepository applicationRepo;

    @Override
    @Transactional
    public Disbursement initiateDisbursement(DisbursementDto dto) {
        log.info("Service: Initiating disbursement for Application ID: {} with amount: {}",
                dto.applicationID(), dto.amount());

        // 1. Reconcile Budget (Business Rule)
        reconcileBudget(dto.programID(), dto.amount());

        // 2. Map and fetch Application
        Disbursement disbursement = ClassUtilSeparator.DisbursementUtil(dto);
        GrantApplication app = applicationRepo.findById(dto.applicationID())
                .orElseThrow(() -> {
                    log.error("Initiation Failed: Application ID {} not found", dto.applicationID());
                    return new DisbursementException("Application not found", HttpStatus.NOT_FOUND);
                });

        disbursement.setApplication(app);

        Disbursement saved = disbursementRepo.save(disbursement);
        log.info("Service Success: Disbursement record saved with ID: {}", saved.getDisbursementID());

        return saved;
    }

    @Override
    @Transactional
    public void reconcileBudget(Long programId, Double amount) {
        log.info("Checking budget for Program ID: {}. Requested amount: {}", programId, amount);

        Budget budget = budgetRepo.findByProgramProgramID(programId)
                .orElseThrow(() -> {
                    log.error("Budget Check Failed: No budget found for Program ID: {}", programId);
                    return new DisbursementException("Budget not found for Program ID: " + programId, HttpStatus.NOT_FOUND);
                });

        if (budget.getRemainingAmount() < amount) {
            log.warn("Insufficient Funds for Program ID {}: Available {}, Requested {}",
                    programId, budget.getRemainingAmount(), amount);
            throw new DisbursementException("Insufficient funds! Available: " + budget.getRemainingAmount(), HttpStatus.BAD_REQUEST);
        }

        // Update budget values
        budget.setSpentAmount(budget.getSpentAmount() + amount);
        budget.setRemainingAmount(budget.getAllocatedAmount() - budget.getSpentAmount());

        budgetRepo.save(budget);
        log.info("Budget updated for Program ID {}: New Remaining Amount: {}", programId, budget.getRemainingAmount());
    }

    @Override
    public List<Disbursement> trackByApplication(Long appId) {
        log.info("Fetching disbursements for Application ID: {}", appId);
        List<Disbursement> results = disbursementRepo.findByApplication_ApplicationID(appId);
        log.info("Found {} records for Application ID: {}", results.size(), appId);
        return results;
    }

    @Override
    public List<Disbursement> trackByStatus(String status) {
        log.info("Filtering disbursements by status: {}", status);
        return disbursementRepo.findByStatus(status);
    }

    @Override
    @Transactional
    public void deleteDisbursement(Long id) {
        log.info("Attempting to delete disbursement record ID: {}", id);

        if (!disbursementRepo.existsById(id)) {
            log.error("Delete Failed: Disbursement ID {} does not exist", id);
            throw new DisbursementException("Cannot delete: Disbursement ID not found", HttpStatus.NOT_FOUND);
        }

        disbursementRepo.deleteById(id);
        log.info("Successfully deleted disbursement record ID: {}", id);
    }
}