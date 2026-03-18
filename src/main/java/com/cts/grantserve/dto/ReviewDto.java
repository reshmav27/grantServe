package com.cts.grantserve.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record ReviewDto(
        @NotNull(message = "Proposal ID is required")
        Long proposalId,

        @NotNull(message = "Reviewer ID is required")
        Long reviewerId,

        @Min(value = 1, message = "Score must be at least 1")
        @Max(value = 10, message = "Score cannot be more than 10")
        Integer score,

        @NotBlank(message = "Comments cannot be empty")
        @Size(min = 10, message = "Comments should be at least 10 characters")
        String comments,

        @NotNull(message = "Review date is required")
        LocalDate date,

        @NotBlank(message = "Status is required")
        String status
) {}