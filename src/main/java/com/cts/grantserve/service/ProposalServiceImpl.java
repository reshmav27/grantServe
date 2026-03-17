package com.cts.grantserve.service;

import com.cts.grantserve.dto.ProposalDto;
import com.cts.grantserve.Repository.IGrantApplicationRepository;
import com.cts.grantserve.Repository.IProposalRepository;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.exception.ProposalException;
import com.cts.grantserve.projection.IProposalProjection;
import com.cts.grantserve.util.ClassUtilSeparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProposalServiceImpl implements  IProposalService {
    @Autowired
    IProposalRepository proposalDao;
    @Autowired
    IGrantApplicationRepository grantApplicationRepository;


    public String createProposal(ProposalDto proposalDto) throws ProposalException {
        Proposal proposal = ClassUtilSeparator.proposalProjection(proposalDto);

        GrantApplication application = grantApplicationRepository.findById(proposalDto.getApplicationID())
                .orElseThrow(() -> new ProposalException("Application Not found",HttpStatus.NOT_FOUND));
        proposal.setGrantApplication(application);

        proposalDao.save(proposal);
        return "Created SuccessFully";
    }

    public List<IProposalProjection> getProposal(Long id){
        return proposalDao.findProjectedById(id);
    }
}
