package com.cts.grantserve.DTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ResearcherDto {

        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        private String name;

        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        private LocalDate dob;

        @NotBlank(message = "Gender is required")
        @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
        private String gender;

        @NotBlank(message = "Institution is required")
        private String institution;

        @NotBlank(message = "Department is required")
        private String department;

        @NotBlank(message = "Contact information is required")
        @Email(message = "Please provide a valid email address")
        private String contactInfo;


    }
