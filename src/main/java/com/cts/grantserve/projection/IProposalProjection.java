package com.cts.grantserve.projection;

import java.time.LocalDateTime;
public interface IProposalProjection {
        Long getProposalID();
        String getFileURI();
        LocalDateTime getSubmittedDate();
        String getStatus();
        Long getApplicationID();
    }
