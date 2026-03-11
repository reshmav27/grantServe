package com.cts.grantserve.service;

import com.cts.grantserve.dao.IProposalDao;
import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@Service
public class ProposalService {
    @Autowired
    IProposalDao proposalDao;
    public String createProposal(Proposal proposal) throws ProposalException {
        proposalDao.save(proposal);
        return "Created SuccessFully";
    }
}
