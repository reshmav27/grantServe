package com.cts.grantserve.projection;

import com.cts.grantserve.enums.BudgetStatus;

public interface IBudgetProjection {
    Long getBudgetID();
    Long getProgramId();
    Double getAllocatedAmount();
    Double getSpentAmount();
    Double getRemainingAmount();
    BudgetStatus getStatus();
}