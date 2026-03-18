package com.cts.grantserve.service;

import com.cts.grantserve.dto.DisbursementDto;
import com.cts.grantserve.entity.Budget;
import com.cts.grantserve.entity.Disbursement;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.exception.DisbursementException;
import com.cts.grantserve.repository.BudgetRepository;
import com.cts.grantserve.repository.DisbursementRepository;
import com.cts.grantserve.repository.IGrantApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
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
        // Business Rule: Check budget before creating disbursement
        reconcileBudget(dto.getProgramID(), dto.getAmount());

        GrantApplication app = applicationRepo.findById(dto.getApplicationID())
                .orElseThrow(() -> new DisbursementException("Application not found", HttpStatus.NOT_FOUND));

        Disbursement disbursement = new Disbursement();
        disbursement.setAmount(dto.getAmount());
        disbursement.setDate(LocalDate.now());
        disbursement.setStatus("PENDING");
        disbursement.setApplication(app);
        return disbursementRepo.save(disbursement);
    }

    @Override
    @Transactional
    public void reconcileBudget(Long programId, Double amount) {
        Budget budget = budgetRepo.findByProgramProgramID(programId)
                .orElseThrow(() -> new DisbursementException("Budget not found for Program ID: " + programId, HttpStatus.NOT_FOUND));

        if (budget.getRemainingAmount() < amount) {
            throw new DisbursementException("Insufficient funds! Available: " + budget.getRemainingAmount(), HttpStatus.BAD_REQUEST);
        }

        budget.setSpentAmount(budget.getSpentAmount() + amount);
        budget.setRemainingAmount(budget.getAllocatedAmount() - budget.getSpentAmount());
        budgetRepo.save(budget);
    }

    @Override
    public List<Disbursement> trackByApplication(Long appId) {
        return disbursementRepo.findByApplication_ApplicationID(appId);
    }

    @Override
    public List<Disbursement> trackByStatus(String status) {
        return disbursementRepo.findByStatus(status);
    }

    @Override
    @Transactional
    public void deleteDisbursement(Long id) {
        if (!disbursementRepo.existsById(id)) {
            throw new DisbursementException("Cannot delete: Disbursement ID not found", HttpStatus.NOT_FOUND);
        }
        disbursementRepo.deleteById(id);
    }
}