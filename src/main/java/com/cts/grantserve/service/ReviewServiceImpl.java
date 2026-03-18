package com.cts.grantserve.service;

import com.cts.grantserve.repository.ReviewRepository;
import com.cts.grantserve.repository.IProposalRepository;
import com.cts.grantserve.dto.ReviewDto;
import com.cts.grantserve.entity.Review;
import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.exception.ReviewNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewServiceImpl implements IReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private IProposalRepository proposalRepository;

    @Override
    public String assignReviewer(ReviewDto reviewDto) {
        Review review = new Review();
        Proposal proposal = proposalRepository.findById(reviewDto.getProposalId())
                .orElseThrow(() -> new RuntimeException("Proposal not found"));
        review.setProposal(proposal);
        review.setReviewerID(reviewDto.getReviewerId());
        review.setStatus("Pending");
        review.setDate(reviewDto.getDate());
        reviewRepository.save(review);
        return "Review assigned successfully";
    }

    @Override
    public List<Review> getReviewsByReviewer(long reviewerId) {
        return reviewRepository.findByReviewerID(reviewerId);
    }

    @Override
    public Review getReviewById(long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review ID " + id + " not found"));
    }

    @Override
    public String updateReview(long id, ReviewDto reviewDto) {
        Review existing = getReviewById(id);
        existing.setScore(reviewDto.getScore());
        existing.setComments(reviewDto.getComments());
        existing.setStatus(reviewDto.getStatus());
        reviewRepository.save(existing);
        return "Review updated successfully";
    }

    @Override
    public String deleteReview(long id) {
        Review review = getReviewById(id);
        reviewRepository.delete(review);
        return "Review deleted successfully";
    }
}