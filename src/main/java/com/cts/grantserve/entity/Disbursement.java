package com.cts.grantserve.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Disbursement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long disbursementID;
    private Double amount;
    private java.time.LocalDate date;
    private String status;
    @ManyToOne
    @JoinColumn(name = "applicationID")
    @JsonBackReference
    private GrantApplication application;
    @OneToOne(mappedBy = "disbursement", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Payment payment;

    public Long getDisbursementID() {
        return disbursementID;
    }

    public void setDisbursementID(Long disbursementID) {
        this.disbursementID = disbursementID;
    }

    public GrantApplication getApplication() {
        return application;
    }

    public void setApplication(GrantApplication application) {
        this.application = application;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}