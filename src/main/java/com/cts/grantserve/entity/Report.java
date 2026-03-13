package com.cts.grantserve.entity;
import com.cts.grantserve.enums.ReportScope;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportID;

    @Enumerated(EnumType.STRING)
    private ReportScope scope;

    private String metrics; // Usually stored as JSON string
    private java.time.LocalDate generatedDate;

    public Long getReportID() {
        return reportID;
    }

    public void setReportID(Long reportID) {
        this.reportID = reportID;
    }

    public ReportScope getScope() {
        return scope;
    }

    public void setScope(ReportScope scope) {
        this.scope = scope;
    }

    public String getMetrics() {
        return metrics;
    }

    public void setMetrics(String metrics) {
        this.metrics = metrics;
    }

    public LocalDate getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(LocalDate generatedDate) {
        this.generatedDate = generatedDate;
    }
}