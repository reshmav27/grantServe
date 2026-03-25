package com.cts.grantserve.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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

    @OneToMany(mappedBy = "proposal", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Review> review = new ArrayList<>();;

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

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