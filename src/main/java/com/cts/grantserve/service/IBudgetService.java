package com.cts.grantserve.service;

import com.cts.grantserve.dto.BudgetDto;
import com.cts.grantserve.entity.Budget;

import java.util.List;
import java.util.Optional;

public interface IBudgetService {

    String createBudget(BudgetDto budgetDto);

    Optional<Budget> getBudget(Long id);

    Optional<Budget> getBudgetByProgram(Long programId);

    List<Budget> getAllBudgets();

    String allocateAmountToResearcherByBudgetId(Long id, double allocatedAmount);
}