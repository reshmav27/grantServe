package com.cts.grantserve.service;

import com.cts.grantserve.dto.BudgetDto;
import com.cts.grantserve.entity.Budget;
import com.cts.grantserve.projection.IBudgetProjection;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface IBudgetService {

    @Transactional
    BudgetDto createBudget(BudgetDto budgetDto);

    Optional<IBudgetProjection> getBudget(Long id);

    Optional<Budget> getBudgetByProgram(Long programId);

    List<IBudgetProjection> getAllBudgets();

    @Transactional
    String allocateAmountToResearcherByBudgetId(Long id, double allocatedAmount);

    @Transactional
    String updateBudgetStatusToClosed(Long programId);

}