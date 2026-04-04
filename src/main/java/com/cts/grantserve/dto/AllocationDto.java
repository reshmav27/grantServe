package com.cts.grantserve.dto;

import jakarta.validation.constraints.NotNull;

public record AllocationDto(
        @NotNull(message = "Application ID must be provided")
        Long applicationID
) {
}
