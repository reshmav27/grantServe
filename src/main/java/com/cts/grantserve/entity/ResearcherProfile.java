package com.cts.grantserve.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="Researcher")
public class ResearcherProfile {

    @Id
    private String researcherID;
    private String name;
    private LocalDate dob;
    private String gender;
    private String institution;
    private String department;
    private String contactInfo;
    private String status;

    // Getter and Setter Methods
    public String getResearcherID()
    {
        return researcherID;
    }
    public void setResearcherID(String researcherID)
    {
        this.researcherID = researcherID;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public LocalDate getDob()
    {
        return dob;
    }
    public void setDob(LocalDate dob)
    {
        this.dob = dob;
    }
    public String getGender()
    {
        return gender;
    }
    public void setGender(String gender)
    {
        this.gender = gender;
    }
    public String getInstitution()
    {
        return institution;
    }
    public void setInstitution(String institution)
    {
        this.institution = institution;
    }
    public String getDepartment()
    {
        return department;
    }
    public void setDepartment(String department)
    {
        this.department = department;
    }
    public String getContactInfo()
    {
        return contactInfo;
    }
    public void setContactInfo(String contactInfo)
    {
        this.contactInfo = contactInfo;
    }
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
}
