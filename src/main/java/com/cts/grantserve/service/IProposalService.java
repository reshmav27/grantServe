package com.cts.grantserve.service;

import com.cts.grantserve.dto.ProposalDto;
import com.cts.grantserve.exception.ProposalException;

public interface IProposalService {
    public String createProposal(ProposalDto proposal) throws ProposalException;
}

