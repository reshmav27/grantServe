package com.cts.grantserve.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GrantApplicationDto(
        @NotBlank(message = "Researcher ID is required")
        String researcherID,

        @NotNull(message = "Program ID is required")
        Long programID,

        @NotBlank(message = "Grant title is required")
        String title
) {}