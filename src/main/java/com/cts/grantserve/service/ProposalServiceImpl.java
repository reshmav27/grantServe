package com.cts.grantserve.service;

import com.cts.grantserve.DTO.ProposalDto;
import com.cts.grantserve.repository.IGrantApplicationRepository;
import com.cts.grantserve.repository.IProposalRepository;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.exception.ProposalException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProposalServiceImpl implements  IProposalService {
    @Autowired
    IProposalRepository proposalDao;
    @Autowired
    IGrantApplicationRepository grantApplicationRepository;
    @Autowired
    private GrantApplicationServiceImpl grantApplicationServiceImpl;

    public String createProposal(ProposalDto proposalDto) throws ProposalException {
        Proposal proposal = new Proposal();
        BeanUtils.copyProperties(proposalDto,proposal);
        proposal.setStatus("Pending");
        GrantApplication application = grantApplicationRepository.findById(proposalDto.getApplicationID())
                .orElseThrow(() -> new ProposalException("Application Not found",HttpStatus.NOT_FOUND));
        proposal.setGrantApplication(application);


        proposal.setSubmittedDate(LocalDateTime.now());
        proposalDao.save(proposal);
        return "Created SuccessFully";
    }
}
