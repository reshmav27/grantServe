package com.cts.grantserve.entity;

import com.cts.grantserve.enums.ComplianceType;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class ComplianceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complianceID;

    private Long entityID; // Can be ApplicationID or ProgramID

    @Enumerated(EnumType.STRING)
    private ComplianceType type;

    private String result;
    private java.time.LocalDate date;
    private String notes;

    public Long getComplianceID() {
        return complianceID;
    }

    public void setComplianceID(Long complianceID) {
        this.complianceID = complianceID;
    }

    public Long getEntityID() {
        return entityID;
    }

    public void setEntityID(Long entityID) {
        this.entityID = entityID;
    }

    public ComplianceType getType() {
        return type;
    }

    public void setType(ComplianceType type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
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