package com.cts.grantserve.projection;

import com.cts.grantserve.entity.Review;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;

public interface IProposalProjection {
        Long getProposalID();
        String getFileURI();
        LocalDateTime getSubmittedDate();
        String getStatus();

        @Value("#{target.grantApplication.applicationID}")
        Long getApplicationID();
    }
