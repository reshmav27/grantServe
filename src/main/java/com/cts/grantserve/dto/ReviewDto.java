package com.cts.grantserve.dto;



import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDto {

    @NotNull(message = "Proposal ID is required")
    private Long proposalId;

    @NotNull(message = "Reviewer ID is required")
    private Long reviewerId;

    @Min(value = 1, message = "Score must be at least 1")
    @Max(value = 10, message = "Score cannot be more than 10")
    private Integer score;

    @NotBlank(message = "Comments cannot be empty")
    @Size(min = 10, message = "Comments should be at least 10 characters")
    private String comments;

    @NotNull(message = "Review date is required")
    private LocalDate date;

    @NotBlank(message = "Status is required")
    private String status;


}