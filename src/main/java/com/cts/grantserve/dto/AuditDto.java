package com.cts.grantserve.dto;

import com.cts.grantserve.enums.AuditScope;
import com.cts.grantserve.enums.AuditStatus;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AuditDto (

    Long auditID,

    @NotNull(message = "Officer ID is mandatory")
    Long officerID,

    @NotNull(message = "Audit scope is mandatory")
    AuditScope scope,

    @NotBlank(message = "Findings cannot be empty")
    @Size(max = 2000, message = "Findings must not exceed 2000 characters")
    String findings,

    @NotNull(message = "Date is mandatory")
    @PastOrPresent(message = "Audit date cannot be in the future")
    LocalDate date,

    @NotNull(message = "Audit status is mandatory")
    AuditStatus status
    ){}
