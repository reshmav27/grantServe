package com.cts.grantserve.service;

import com.cts.grantserve.dto.ProposalDto;
import com.cts.grantserve.entity.Review;
import com.cts.grantserve.exception.ReviewNotFoundException;
import com.cts.grantserve.repository.IGrantApplicationRepository;
import com.cts.grantserve.repository.IProposalRepository;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.exception.ProposalException;
import com.cts.grantserve.projection.IProposalProjection;
import com.cts.grantserve.repository.ReviewRepository;
import com.cts.grantserve.util.ClassUtilSeparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class ProposalServiceImpl implements  IProposalService {
    @Autowired
    IProposalRepository proposalDao;
    @Autowired
    IGrantApplicationRepository grantApplicationRepository;

    @Autowired
    ReviewRepository reviewRepository;


    public String createProposal(ProposalDto proposalDto) throws ProposalException {
        Proposal proposal = ClassUtilSeparator.proposalUtil(proposalDto);

        GrantApplication application = grantApplicationRepository.findById(proposalDto.applicationID())
                .orElseThrow(() -> new ProposalException("Application Not found", HttpStatus.NOT_FOUND));

        proposal.setGrantApplication(application);
        proposal.setStatus("SUBMITTED"); // Initial status

        proposalDao.save(proposal);
        return "Proposal submitted successfully with ID: " + proposal.getProposalID();
    }

    public String addReviewToProposal(Long proposalId, Review review) {
        Proposal proposal = proposalDao.findById(proposalId)
                .orElseThrow(() -> new RuntimeException("Proposal not found"));
        review.setProposal(proposal);
        proposal.setStatus("UNDER_REVIEW");
        reviewRepository.save(review);
        return "Review added successfully";
    }

    public List<IProposalProjection> getProposal(Long id){
        return proposalDao.findProjectedById(id);
    }
}
