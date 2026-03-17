package com.cts.grantserve.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

import com.cts.grantserve.enums.ProgramStatus;

@Entity
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long programID;

    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double budget;
    private ProgramStatus status;

    @OneToOne(mappedBy = "program", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Budget budgetRecord;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<GrantApplication> applications;

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

    public ProgramStatus getStatus() {
        return status;
    }

    public void setStatus(ProgramStatus status) {
        this.status = status;
    }

    public Budget getBudgetRecord() {
        return budgetRecord;
    }

    public void setBudgetRecord(Budget budgetRecord) {
        this.budgetRecord = budgetRecord;
    }

    public List<GrantApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<GrantApplication> applications) {
        this.applications = applications;
    }
}