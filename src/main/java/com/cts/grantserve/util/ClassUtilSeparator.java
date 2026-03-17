package com.cts.grantserve.util;

import com.cts.grantserve.dto.ProposalDto;
import com.cts.grantserve.entity.Proposal;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClassUtilSeparator {
    public static Proposal proposalProjection(ProposalDto proposalDto){
        Proposal proposal =new Proposal();
        proposal.setFileURI(proposalDto.getFileURI());
        proposal.setSubmittedDate(LocalDateTime.now());
        proposal.setStatus("Submitted");
        return  proposal;
    }
}
