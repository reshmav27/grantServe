package com.cts.grantserve.service;

import com.cts.grantserve.repository.DisbursementRepository;
import com.cts.grantserve.repository.PaymentRepository;
import com.cts.grantserve.entity.Disbursement;
import com.cts.grantserve.entity.Payment;
import com.cts.grantserve.entity.Budget;
import com.cts.grantserve.enums.PaymentMethod;
import com.cts.grantserve.exception.DisbursementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class DisbursementService {

    @Autowired
    private DisbursementRepository disbursementRepo;

    @Autowired
    private PaymentRepository paymentRepo;

//    @Autowired
//    private BudgetRepository budgetDao;

    @Transactional
    public Disbursement initiateDisbursement(Disbursement disbursement, Long programId) {
        reconcileBudget(programId, disbursement.getAmount());
        disbursement.setDate(LocalDate.now());
        disbursement.setStatus("PENDING");
        return disbursementRepo.save(disbursement);
    }

    @Transactional
    public void reconcileBudget(Long programId, Double amount) {

//        Budget budget = budgetDao.getBudgetByProgramID(programId);
//
//        if (budget == null) {
//            throw new DisbursementException("Budget record not found for Program ID: " + programId);
//        }
//        if (budget.getRemainingAmount() < amount) {
//            throw new DisbursementException("Insufficient funds! Available: " + budget.getRemainingAmount());
//        }
//        budget.setSpentAmount(budget.getSpentAmount() + amount);
//        budget.setRemainingAmount(budget.getAllocatedAmount() - budget.getSpentAmount());
//        budgetDao.save(budget);
    }

    public List<Disbursement> trackByApplication(Long appId) {
        return disbursementRepo.findByApplication_ApplicationID(appId);
    }

    public List<Disbursement> trackByStatus(String status) {
        return disbursementRepo.findByStatus(status);
    }

    public List<Payment> monitorByMethod(PaymentMethod method) {
        return paymentRepo.findByMethod(method);
    }

    public void deleteDisbursement(Long id) {
        disbursementRepo.deleteById(id);
    }
}