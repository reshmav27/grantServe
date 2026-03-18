package com.cts.grantserve.DTO;

import com.cts.grantserve.enums.ProgramStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public record ProgramDto(
        Long programID,

        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "Start date is required")
        @PastOrPresent(message = "Start date cannot be in the future")
        LocalDate startDate,

        @NotNull(message = "End date is required")
        LocalDate endDate,

        @NotNull(message = "Budget is required")
        Double budget,

        @NotNull(message = "Status is required")
        ProgramStatus status
) {
    // Compact constructor for validation logic
    public ProgramDto {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        if (budget != null && budget < 0) {
            throw new IllegalArgumentException("Budget must be a valid positive value");
        }
    }
}