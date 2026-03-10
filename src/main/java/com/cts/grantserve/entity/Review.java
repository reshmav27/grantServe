package com.cts.grantserve.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="Review")
public class Review {
    @Id
    private Long reviewID;
    private Long proposalID;
    private Long reviewerID;
    private Integer score;
    private String comments;
    private LocalDateTime date;
    private String status;

    public Long getReviewID()
    {
        return reviewID;
    }

    public void setReviewID(Long reviewID)
    {
        this.reviewID = reviewID;
    }

    public Long getProposalID()
    {
        return proposalID;
    }

    public void setProposalID(Long proposalID)
    {
        this.proposalID = proposalID;
    }

    public Long getReviewerID()
    {
        return reviewerID;
    }

    public void setReviewerID(Long reviewerID)
    {
        this.reviewerID = reviewerID;
    }

    public Integer getScore()
    {
        return score;
    }

    public void setScore(Integer score)
    {
        this.score = score;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public void setDate(LocalDateTime date)
    {
        this.date = date;
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
