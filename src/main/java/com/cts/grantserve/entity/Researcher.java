package com.cts.grantserve.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Researcher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long researcherID;
    private String name;
    private LocalDate dob;
    private String gender;
    private String institution;
    private String department;
    private String contactInfo;

    @OneToOne
    @JoinColumn(name = "user_id") // The foreign key column in Researcher table
    private User user;

    // Standard Getter/Setter
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @OneToMany(mappedBy = "researcher", cascade = CascadeType.ALL)
    private List<ResearcherDocument> documents; // One researcher has many docs [cite: 1, 85]

    @OneToMany(mappedBy = "researcher")
    @JsonManagedReference(value = "researcher-applications")
    private List<GrantApplication> applications;

    public List<ResearcherDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<ResearcherDocument> documents) {
        this.documents = documents;
    }

    public List<GrantApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<GrantApplication> applications) {
        this.applications = applications;
    }

    public Long getResearcherID() {
        return researcherID;
    }

    public void setResearcherID(Long researcherID) {
        this.researcherID = researcherID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    private String status;

}