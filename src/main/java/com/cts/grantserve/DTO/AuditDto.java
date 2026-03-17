package com.cts.grantserve.DTO;

import com.cts.grantserve.enums.AuditScope;
import com.cts.grantserve.enums.AuditStatus;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Getter
@Setter
public class AuditDto {

    private Long auditID;

    @NotNull(message = "Officer ID is mandatory")
    private Long officerID;

    @NotNull(message = "Audit scope is mandatory")
    private AuditScope scope;

    @NotBlank(message = "Findings cannot be empty")
    @Size(max = 2000, message = "Findings must not exceed 2000 characters")
    private String findings;

    @NotNull(message = "Date is mandatory")
    @PastOrPresent(message = "Audit date cannot be in the future")
    private LocalDate date;

    @NotNull(message = "Audit status is mandatory")
    private AuditStatus status;
}
