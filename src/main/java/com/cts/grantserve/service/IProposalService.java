package com.cts.grantserve.service;

import com.cts.grantserve.DTO.ProposalDto;
import com.cts.grantserve.Repository.IProposalRepository;
import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;

public interface IProposalService {
    public String createProposal(ProposalDto proposal) throws ProposalException;
}

