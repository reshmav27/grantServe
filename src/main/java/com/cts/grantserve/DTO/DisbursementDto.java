package com.cts.grantserve.DTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DisbursementDto {

    @NotNull(message = "Application ID is required")
    private Long applicationID;

    @NotNull(message = "Program ID is required")
    private Long programID;

    @NotNull(message = "Disbursement amount is required")
    @Positive(message = "Amount must be greater than zero")
    private Double amount;
}