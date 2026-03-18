package com.cts.grantserve.dto;


import com.cts.grantserve.enums.Result;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.time.LocalDate;
@Data
public class EvaluationDto {

    @NotNull(message = "Application ID is required")
    private Long applicationID;

    @NotNull(message = "Result (APPROVED/REJECTED) is required")
    private Result result;

    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Evaluation date cannot be in the future")
    private LocalDate date;

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String notes;


}