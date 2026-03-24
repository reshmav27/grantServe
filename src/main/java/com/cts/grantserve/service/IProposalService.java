package com.cts.grantserve.service;

import com.cts.grantserve.dto.ProposalDto;
import com.cts.grantserve.entity.Program;
import com.cts.grantserve.exception.ProposalException;
import com.cts.grantserve.projection.IProposalProjection;

import java.time.LocalDateTime;
import java.util.List;

public interface IProposalService {
    public String createProposal(ProposalDto proposal) throws ProposalException;

    List<IProposalProjection> getProposal(Long id);


}

