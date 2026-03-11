package com.cts.grantserve.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "disbursement")
public class Disbursement {
    @Id
    private Long disbursementID;
    private Long applicationID;
    private Double amount;
    private LocalDateTime date;
    private String status;




    // Getters and Setters
    public Long getDisbursementID() {
        return disbursementID;
    }

    public void setDisbursementID(Long disbursementID) {
        this.disbursementID = disbursementID;
    }

    public Long getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(Long applicationID) {
        this.applicationID = applicationID;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
