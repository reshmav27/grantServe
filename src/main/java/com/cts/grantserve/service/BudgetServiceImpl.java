package com.cts.grantserve.service;

import com.cts.grantserve.dto.BudgetDto;
import com.cts.grantserve.entity.Budget;
import com.cts.grantserve.entity.Program;
import com.cts.grantserve.exception.BudgetNotFoundException;
import com.cts.grantserve.exception.InsufficientFundsException;
import com.cts.grantserve.exception.ProgramNotFoundException;
import com.cts.grantserve.repository.BudgetRepository;
import com.cts.grantserve.repository.ProgramRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetServiceImpl implements IBudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Transactional
    @Override
    public String createBudget(BudgetDto budgetDto) {
        Budget budget = new Budget();
        budget.setAllocatedAmount(budgetDto.allocatedAmount());
        budget.setSpentAmount(budgetDto.spentAmount());
        budget.setRemainingAmount(budgetDto.remainingAmount());
        budget.setStatus(budgetDto.status());

        Optional<Program> programOpt = programRepository.findById(budgetDto.programId());
        if (programOpt.isEmpty()) {
            throw new ProgramNotFoundException("Program not found with id: " + budgetDto.programId());
        }
        budget.setProgram(programOpt.get());

        budgetRepository.save(budget);
        return "Budget created successfully";
    }

//    @Transactional
//    @Override
//    public String updateBudget(Long id, BudgetDto budgetDto) {
//        Optional<Budget> existingBudgetOpt = budgetRepository.findById(id);
//        if (existingBudgetOpt.isEmpty()) {
//            throw new BudgetNotFoundException("Budget not found with id: " + id);
//        }
//        Budget existingBudget = existingBudgetOpt.get();
//        BeanUtils.copyProperties(budgetDto, existingBudget);
//        existingBudget.setBudgetID(id); // Ensure ID is preserved
//
//        // Update program reference if changed
//        if (!existingBudget.getProgram().getProgramID().equals(budgetDto.programId())) {
//            Optional<Program> programOpt = programRepository.findById(budgetDto.programId());
//            if (programOpt.isEmpty()) {
//                throw new ProgramNotFoundException("Program not found with ID: " + budgetDto.programId());
//            }
//            existingBudget.setProgram(programOpt.get());
//        }
//
//        budgetRepository.save(existingBudget);
//        return "Budget updated successfully";
//    }

    @Transactional
    @Override
    public String allocateAmountToResearcherByBudgetId(Long id, double allocatedAmount) {
        Optional<Budget> existingBudgetOpt = budgetRepository.findById(id);
        if (existingBudgetOpt.isEmpty()) {
            throw new BudgetNotFoundException("Budget not found with id: " + id);
        }

        Budget existingBudget = existingBudgetOpt.get();
        if (existingBudget.getRemainingAmount() < allocatedAmount) {
            throw new InsufficientFundsException(
                    "Insufficient funds. Requested: " + allocatedAmount +
                    ", Available: " + existingBudget.getRemainingAmount()
            );
        }

        existingBudget.setSpentAmount(existingBudget.getSpentAmount() + allocatedAmount);
        existingBudget.setRemainingAmount(existingBudget.getRemainingAmount() - allocatedAmount);

        budgetRepository.save(existingBudget);
        return "Amount allocated to researcher successfully";
    }

    @Override
    public Optional<Budget> getBudget(Long id) {
        return budgetRepository.findById(id);
    }

    @Override
    public Optional<Budget> getBudgetByProgram(Long programId) {
        return budgetRepository.findByProgramProgramID(programId);
    }

    @Override
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }
}