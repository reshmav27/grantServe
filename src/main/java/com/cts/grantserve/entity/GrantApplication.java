package com.cts.grantserve.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity

public class GrantApplication {
    @Id
    private Long applicationID;
    private String researcherID;
    private Long programID;
    private String title;
    private LocalDateTime submittedDate;
    private String status;

    public Long getApplicationID()
    {
        return applicationID;
    }

    public void setApplicationID(Long applicationID)
    {
        this.applicationID = applicationID;
    }

    public String getResearcherID()
    {
        return researcherID;
    }

    public void setResearcherID(String researcherID)
    {
        this.researcherID = researcherID;
    }

    public Long getProgramID()
    {
        return programID;
    }

    public void setProgramID(Long programID)
    {
        this.programID = programID;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public LocalDateTime getSubmittedDate()
    {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDateTime submittedDate)
    {
        this.submittedDate = submittedDate;
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
