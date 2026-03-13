package com.cts.grantserve.service;

import com.cts.grantserve.dao.ReviewDao;
import com.cts.grantserve.entity.Review;
import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.exception.ReviewNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewDao reviewDao;

    public List<Review> getReviewsByReviewer(long reviewerId) {

        return reviewDao.findByReviewerID(reviewerId);
    }

    public Review getReviewById(long id) {
        return reviewDao.findById(id).orElseThrow(() -> new ReviewNotFoundException("Review with ID " + id + " not found"));
    }

// Inside ReviewService.java
   /* @Autowired
    private IProposalDao proposalDao;

    public String assignReviewer(long proposalId, long reviewerId) {
        Review newReview = new Review();

        // 1. Fetch the actual Proposal object from the DB
        Proposal proposal = proposalDao.findById(proposalId)
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        // 2. Set the Object, not just the ID
        newReview.setProposal(proposal);

        newReview.setReviewerID(reviewerId);
        newReview.setStatus("Pending");

        reviewRepository.save(newReview);
        return "Assigned Successfully";
    }*/
}