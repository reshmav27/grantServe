package com.cts.grantserve.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="Proposal")
public class Proposal {
    @Id
    private Long proposalID;
    private Long applicationID;
    private String fileURI;
    private LocalDateTime submittedDate;
    private String status;

    public Long getProposalID() {
        return proposalID;
    }

    public void setProposalID(Long proposalID) {
        this.proposalID = proposalID;
    }

    public Long getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(Long applicationID) {
        this.applicationID = applicationID;
    }

    public String getFileURI() {
        return fileURI;
    }

    public void setFileURI(String fileURI) {
        this.fileURI = fileURI;
    }

    public LocalDateTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
