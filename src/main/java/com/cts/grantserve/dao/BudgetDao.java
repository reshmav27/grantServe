package com.cts.grantserve.dao;

import com.cts.grantserve.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetDao extends JpaRepository<Budget, Long> {
    Budget getBudgetByProgramID(Long programId);
    Budget getByBudgetID(Long budgetId);
    boolean allocateAmount(Long programId, Double amount);
}