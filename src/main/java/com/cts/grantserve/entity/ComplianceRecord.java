package com.cts.grantserve.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity

public class ComplianceRecord {
    @Id
    private Long complianceID;
    private Long entityID;
    private String type; // Application/Program
    private String result;
    private LocalDateTime date;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
