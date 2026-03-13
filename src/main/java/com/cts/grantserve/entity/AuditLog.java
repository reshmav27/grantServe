package com.cts.grantserve.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditID;
    private String action;
    private String resource;
    private java.time.LocalDateTime timestamp;
    @ManyToOne
    @JoinColumn(name = "userID",nullable = false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getAuditID() {
        return auditID;
    }

    public void setAuditID(Long auditID) {
        this.auditID = auditID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}