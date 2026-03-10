package com.cts.grantserve.entity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proposalID;

    public GrantApplication getApplication() {
        return application;
    }

    public void setApplication(GrantApplication application) {
        this.application = application;
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

    public LocalDate getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDate submittedDate) {
        this.submittedDate = submittedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToOne
    @JoinColumn(name = "applicationID")
    private GrantApplication application;

    @OneToMany(mappedBy = "proposal")
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    private String fileURI;
    private java.time.LocalDate submittedDate;
    private String status;
}