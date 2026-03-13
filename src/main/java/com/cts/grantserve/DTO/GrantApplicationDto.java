package com.cts.grantserve.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GrantApplicationDto {

        @NotBlank(message = "Researcher ID is required")
        private String researcherID;

        @NotNull(message = "Program ID is required")
        private Long programID;

        @NotBlank(message = "Grant title is required")
        private String title;

}
