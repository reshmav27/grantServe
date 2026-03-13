package com.cts.grantserve.entity;

import jakarta.persistence.*;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditID;
    @ManyToOne
    @JoinColumn(name = "officerID", referencedColumnName = "userID")
    private User officer;

    public User getOfficer() {
        return officer;
    }

    public void setOfficer(User officer) {
        this.officer = officer;
    }

    private String scope;
    private String findings;
    private java.time.LocalDate date;
    private String status;

    public Long getAuditID() {
        return auditID;
    }

    public void setAuditID(Long auditID) {
        this.auditID = auditID;
    }


    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getFindings() {
        return findings;
    }

    public void setFindings(String findings) {
        this.findings = findings;
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
}

