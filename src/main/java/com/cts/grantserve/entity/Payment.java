package com.cts.grantserve.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name ="Payment")
public class Payment {
    @Id
    private Long paymentID;
    private Long disbursementID;
    private String method; // Bank/Wallet
    private LocalDateTime date;
    private String status;



    public Long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Long paymentID) {
        this.paymentID = paymentID;
    }

    public Long getDisbursementID() {
        return disbursementID;
    }

    public void setDisbursementID(Long disbursementID) {
        this.disbursementID = disbursementID;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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
