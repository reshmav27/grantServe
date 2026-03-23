package com.cts.grantserve.dto;

import com.cts.grantserve.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PaymentDto(
        @NotNull(message = "Disbursement ID cannot be null")
        Long disbursementID,

        @NotNull(message = "Payment method is required (BANK or WALLET)")
        PaymentMethod method,

        @NotNull(message = "Payment amount is required")
        @Positive(message = "Payment amount must be a positive value")
        Double amount
) {}