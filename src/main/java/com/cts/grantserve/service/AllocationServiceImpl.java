package com.cts.grantserve.service;

import com.cts.grantserve.dto.AllocationDto;
import com.cts.grantserve.entity.Allocation;
import com.cts.grantserve.entity.Budget;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.entity.Program;
import com.cts.grantserve.exception.AllocationException;
import com.cts.grantserve.repository.AllocationRepository;
import com.cts.grantserve.repository.BudgetRepository;
import com.cts.grantserve.repository.IGrantApplicationRepository;
import com.cts.grantserve.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AllocationServiceImpl implements IAllocationService {

    @Autowired
    private IGrantApplicationRepository applicationRepo;

    @Autowired
    private BudgetRepository budgetRepo;

    @Autowired
    private AllocationRepository allocationRepo;

    @Autowired
    private ProgramRepository programRepo;

    @Override
    @Transactional
    public String createAllocation(AllocationDto allocationDto) throws AllocationException {
        // Extract the ID from the Record DTO
        Long applicationId = allocationDto.applicationID();

        // 1. Ensure the application is already APPROVED
        GrantApplication app = applicationRepo.findById(applicationId)
                .orElseThrow(() -> new AllocationException("Application not found", HttpStatus.NOT_FOUND));

        if (!"APPROVED".equalsIgnoreCase(app.getStatus())) {
            throw new AllocationException("Cannot allocate funds: Application status is " + app.getStatus(), HttpStatus.BAD_REQUEST);
        }

        // 2. Fetch Program and Budget
        // Using app.getProgram().getProgramID() to ensure consistency with your mappings
        Long pId = app.getProgram().getProgramID();

        Program program = programRepo.findById(pId)
                .orElseThrow(() -> new AllocationException("Program not found", HttpStatus.NOT_FOUND));

        // Note: Using the specific method name based on your entity mapping
        Budget budget = budgetRepo.findByProgramProgramID(pId)
                .orElseThrow(() -> new AllocationException("Budget not found for Program ID: " + pId, HttpStatus.NOT_FOUND));

        // 3. Calculation Logic: (Total Budget / Program Count)
        if (program.getCount() <= 0) {
            throw new AllocationException("No available slots left in program to allocate funds", HttpStatus.BAD_REQUEST);
        }

        // Calculate the individual share
        Double shareAmount = budget.getAllocatedAmount() / program.getCount();

        // 4. Update Program Table: Subtract the count
        program.setCount(program.getCount() - 1);
        programRepo.save(program);

        // 5. Update Budget Table: Add Spent, update Remaining
        Double currentSpent = (budget.getSpentAmount() != null ? budget.getSpentAmount() : 0.0);
        Double totalSpent = currentSpent + shareAmount;

        if (totalSpent > budget.getAllocatedAmount()) {
            throw new AllocationException("Allocation exceeds total budget limits", HttpStatus.BAD_REQUEST);
        }

        budget.setSpentAmount(totalSpent);
        budget.setRemainingAmount(budget.getAllocatedAmount() - totalSpent);
        budgetRepo.save(budget);

        // 6. Save the record to the Allocation Table
        Allocation allocation = new Allocation();
        allocation.setApplication(app);
        allocation.setProgramID(pId);
        allocation.setBudget(budget);
        allocation.setTotalAwardedAmount(shareAmount);
        allocation.setDisbursedAmount(0.0);
        allocation.setRemainingBalance(shareAmount);
        allocation.setStatus("ALLOCATED");

        allocationRepo.save(allocation);

        return "Successfully allocated " + shareAmount + " to Application ID: " + applicationId;
    }
}