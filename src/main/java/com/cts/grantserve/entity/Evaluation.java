package com.cts.grantserve.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "evaluation")
public class Evaluation {
    @Id
    private Long evaluationID;
    private Long applicationID;
    private boolean result;
    private LocalDateTime date;
    private String notes;

    public Long getEvaluationID()
    {
        return evaluationID;
    }

    public void setEvaluationID(Long evaluationID)
    {
        this.evaluationID = evaluationID;
    }

    public Long getApplicationID()
    {
        return applicationID;
    }

    public void setApplicationID(Long applicationID)
    {
        this.applicationID = applicationID;
    }

    public boolean getResult()
    {
        return result;
    }

    public void setResult(boolean result)
    {
        this.result = result;
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public void setDate(LocalDateTime date)
    {
        this.date = date;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }
}
