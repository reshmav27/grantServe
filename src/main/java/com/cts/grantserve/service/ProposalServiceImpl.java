package com.cts.grantserve.service;

import com.cts.grantserve.dto.ProposalDto;
import com.cts.grantserve.repository.IGrantApplicationRepository;
import com.cts.grantserve.repository.IProposalRepository;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.exception.ProposalException;
import com.cts.grantserve.projection.IProposalProjection;
import com.cts.grantserve.util.ClassUtilSeparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProposalServiceImpl implements  IProposalService {
    @Autowired
    IProposalRepository proposalDao;
    @Autowired
    IGrantApplicationRepository grantApplicationRepository;

    public String createProposal(ProposalDto proposalDto) throws ProposalException {
        Proposal proposal = ClassUtilSeparator.proposalUtil(proposalDto);

        GrantApplication application = grantApplicationRepository.findById(proposalDto.applicationID())
                .orElseThrow(() -> new ProposalException("Application Not found", HttpStatus.NOT_FOUND));

        proposal.setGrantApplication(application);
        proposal.setStatus("SUBMITTED"); // Initial status

        proposalDao.save(proposal);
        return "Proposal submitted successfully with ID: " + proposal.getProposalID();
    }

    public List<IProposalProjection> getProposal(Long id){
        return proposalDao.findProjectedById(id);
    }



    @Override
    public List<IProposalProjection> getRecentProposals(Long applicationId) throws ProposalException {
        log.info("Fetching top 3 most recent proposals for Application ID: {}", applicationId);


        if (!grantApplicationRepository.existsById(applicationId)) {
            throw new ProposalException("Application not found with ID: " + applicationId, HttpStatus.NOT_FOUND);
        }


        List<IProposalProjection> recentProposals = proposalDao.findTop3ByGrantApplication_ApplicationIDOrderByProposalIDDesc(applicationId);

        if (recentProposals.isEmpty()) {
            log.warn("No proposals found for application ID: {}", applicationId);
        }

        return recentProposals;
    }

}
