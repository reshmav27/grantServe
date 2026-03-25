package com.cts.grantserve.projection;

import com.cts.grantserve.entity.Review;

import java.time.LocalDateTime;
import java.util.List;

public interface IProposalProjection {
        Long getProposalID();
        String getFileURI();
        LocalDateTime getSubmittedDate();
        String getStatus();
        Long getApplicationID();
        List<Review> getReview();
    }
