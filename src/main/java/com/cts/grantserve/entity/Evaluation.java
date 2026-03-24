package com.cts.grantserve.entity;
import com.cts.grantserve.enums.Result;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evaluationID;

    @OneToOne
    @JoinColumn(name = "applicationID")
    @JsonIgnore
    private GrantApplication application;

    @Enumerated(EnumType.STRING)
    private Result result;

    private java.time.LocalDate date;
    private String notes;

    public Long getEvaluationID() {
        return evaluationID;
    }

    public void setEvaluationID(Long evaluationID) {
        this.evaluationID = evaluationID;
    }

    public GrantApplication getApplication() {
        return application;
    }

    public void setApplication(GrantApplication application) {
        this.application = application;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}