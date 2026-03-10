package com.cts.grantserve.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programID;
    private String title;
    private String description;
    private java.time.LocalDate startDate;
    private java.time.LocalDate endDate;
    private Double budget;

    public Long getProgramID() {
        return programID;
    }

    public void setProgramID(Long programID) {
        this.programID = programID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Budget getBudgetRecord() {
        return budgetRecord;
    }

    public void setBudgetRecord(Budget budgetRecord) {
        this.budgetRecord = budgetRecord;
    }

    private String status;

    @OneToOne(mappedBy = "program")
    private Budget budgetRecord;
    @OneToMany(mappedBy = "program")
    private List<GrantApplication> applications;

    public List<GrantApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<GrantApplication> applications) {
        this.applications = applications;
    }
}