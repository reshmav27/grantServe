package com.cts.grantserve.dto;

import com.cts.grantserve.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PaymentDto(
        @NotNull(message = "Disbursement ID is required")
        Long disbursementID,

        @NotNull(message = "Payment method is required")
        PaymentMethod method,

        @NotNull
        @Positive(message = "Payment amount must be positive")
        Double amount
) {}