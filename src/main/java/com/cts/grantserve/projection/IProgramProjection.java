package com.cts.grantserve.projection;

import com.cts.grantserve.entity.Budget;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public interface IProgramProjection {
    Long getProgramID();
    String getTitle();
    String getDescription();
    LocalDate getStartDate();
    LocalDate getEndDate();
    Double getBudget();
    Budget getBudgetRecord();

    // Dynamic logic: If status is ACTIVE and startDate is in the future, return FORECASTED
    @Value("#{target.status.name() == 'ACTIVE' && target.startDate.isAfter(T(java.time.LocalDate).now()) ? 'FORECASTED' : target.status}")
    String getStatus();
}