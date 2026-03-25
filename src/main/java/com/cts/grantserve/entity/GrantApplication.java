package com.cts.grantserve.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GrantApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationID;

    public Researcher getResearcher() {
        return researcher;
    }

    public void setResearcher(Researcher researcher) {
        this.researcher = researcher;
    }

    public Long getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(Long applicationID) {
        this.applicationID = applicationID;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    @ManyToOne
    @JoinColumn(name = "researcherID")
    @JsonBackReference(value = "researcher-applications")
    private Researcher researcher;

    @ManyToOne
    @JoinColumn(name = "programID")
    @JsonBackReference(value = "program-applications")
    private Program program;

    @OneToMany(mappedBy = "grantApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Proposal> proposals= new ArrayList<>();

    private String title;
    private java.time.LocalDate submittedDate;
    private String status;
}