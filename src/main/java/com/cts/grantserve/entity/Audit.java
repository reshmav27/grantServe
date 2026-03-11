package com.cts.grantserve.entity;

import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name="audit")
public class Audit {
    @Id
    private Long auditID;
    private Long officerID;
    private String scope;
    private String findings;
    private LocalDateTime date;
    private String status;

    public Long getAuditID() {
        return auditID;
    }

    public void setAuditID(Long auditID) {
        this.auditID = auditID;
    }

    public Long getOfficerID() {
        return officerID;
    }

    public void setOfficerID(Long officerID) {
        this.officerID = officerID;
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




