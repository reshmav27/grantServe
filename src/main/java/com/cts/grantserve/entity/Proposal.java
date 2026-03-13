package com.cts.grantserve.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name="proposal")
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proposalID;
//    private Long applicationID;
    private String fileURI;
    private LocalDateTime submittedDate;
    private String status;
    @ManyToOne
    @JoinColumn(name = "applicationID")
    @JsonBackReference
    private GrantApplication grantApplication;

    public GrantApplication getGrantApplication() {
        return grantApplication;
    }

    public void setGrantApplication(GrantApplication grantApplication) {
        this.grantApplication = grantApplication;
    }

    public Long getProposalID() {
        return proposalID;
    }

    public void setProposalID(Long proposalID) {
        this.proposalID = proposalID;
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