package com.cts.grantserve.entity;

import com.cts.grantserve.enums.BudgetStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Long budgetID;

    private Double allocatedAmount;
    private Double spentAmount;
    private Double remainingAmount;
    private BudgetStatus status;

    @OneToOne
    @JoinColumn(name = "program_id")
    @JsonBackReference
    private Program program;

    public Long getBudgetID() {
        return budgetID;
    }

    public void setBudgetID(Long budgetID) {
        this.budgetID = budgetID;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Double getAllocatedAmount() {
        return allocatedAmount;
    }

    public void setAllocatedAmount(Double allocatedAmount) {
        this.allocatedAmount = allocatedAmount;
    }

    public Double getSpentAmount() {
        return spentAmount;
    }

    public void setSpentAmount(Double spentAmount) {
        this.spentAmount = spentAmount;
    }

    public Double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public BudgetStatus getStatus() {
        return status;
    }

    public void setStatus(BudgetStatus status) {
        this.status = status;
    }
}