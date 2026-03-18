package com.cts.grantserve.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DisbursementDto(
        @NotNull(message = "Application ID is required")
        Long applicationID,

        @NotNull(message = "Program ID is required")
        Long programID,

        @NotNull(message = "Disbursement amount is required")
        @Positive(message = "Amount must be greater than zero")
        Double amount
) {}