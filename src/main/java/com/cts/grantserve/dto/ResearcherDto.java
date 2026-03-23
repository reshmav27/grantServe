package com.cts.grantserve.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record ResearcherDto(

        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        LocalDate dob,

        @NotBlank(message = "Gender is required")
        @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
        String gender,

        @NotBlank(message = "Institution is required")
        String institution,

        @NotBlank(message = "Department is required")
        String department

) {}