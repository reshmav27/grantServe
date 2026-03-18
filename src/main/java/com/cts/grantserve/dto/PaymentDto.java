package com.cts.grantserve.dto;
import com.cts.grantserve.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentDto {
        @NotNull(message = "Disbursement ID is required")
        private Long disbursementID;

        @NotNull(message = "Payment method is required")
        private PaymentMethod method;

        @NotNull
        @Positive(message = "Payment amount must be positive")
        private Double amount;

}