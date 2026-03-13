package com.cts.grantserve.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProposalDto {

    @NotNull(message = "Application ID is required")
    private Long applicationID;

    @NotBlank(message = "File URI cannot be empty")
    @Size(max = 255, message = "File URI is too long")
    private String fileURI;

}
