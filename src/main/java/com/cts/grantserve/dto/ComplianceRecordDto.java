package com.cts.grantserve.dto;

import com.cts.grantserve.enums.ComplianceResult;
import com.cts.grantserve.enums.ComplianceType;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ComplianceRecordDto (
    Long complianceID,

    @NotNull(message = "Entity ID is mandatory and cannot be null")
    Long entityID,

    @NotNull(message = "Compliance type is required")
    ComplianceType type,

    @NotNull(message = "Compliance result is required")
    ComplianceResult result,

    @NotNull(message = "Date is mandatory")
    @PastOrPresent(message = "Compliance check date cannot be in the future")
    LocalDate date,

    @Size(max = 500, message = "Notes must not exceed 500 characters")
    String notes
    ){}
