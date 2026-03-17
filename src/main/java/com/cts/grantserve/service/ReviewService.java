package com.cts.grantserve.service;

import com.cts.grantserve.repository.IProposalRepository;
import com.cts.grantserve.repository.ReviewRepository;
import com.cts.grantserve.entity.Review;
import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.exception.ReviewNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    public List<Review> getReviewsByReviewer(long reviewerId) {

        return reviewRepository.findByReviewerID(reviewerId);
    }

    public Review getReviewById(long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException("Review with ID " + id + " not found"));
    }

// Inside ReviewService.java
   @Autowired
    private IProposalRepository proposalRepository;

    public String assignReviewer(long proposalId, long reviewerId) {
        Review newReview = new Review();

        // 1. Fetch the actual Proposal object from the DB
        Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        // 2. Set the Object, not just the ID
        newReview.setProposal(proposal);

        newReview.setReviewerID(reviewerId);
        newReview.setStatus("Pending");

        reviewRepository.save(newReview);
        return "Assigned Successfully";
    }
}