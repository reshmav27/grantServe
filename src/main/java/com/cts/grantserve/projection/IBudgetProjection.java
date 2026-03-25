package com.cts.grantserve.projection;

import com.cts.grantserve.entity.Program;
import com.cts.grantserve.enums.BudgetStatus;

public interface IBudgetProjection {
    Long getBudgetID();
    Program getProgram();
    Double getAllocatedAmount();
    Double getSpentAmount();
    Double getRemainingAmount();
    BudgetStatus getStatus();
}