package com.cts.grantserve.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record ResearcherDto(
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name,

        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        LocalDate dob,

        @NotBlank(message = "Gender is required")
        @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
        String gender,

        @NotBlank(message = "Institution is required")
        String institution,

        @NotBlank(message = "Department is required")
        String department,

        @NotBlank(message = "Contact information is required")
        @Email(message = "Please provide a valid email address")
        String contactInfo
) {}