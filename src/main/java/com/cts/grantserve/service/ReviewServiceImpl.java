package com.cts.grantserve.service;

import com.cts.grantserve.repository.ReviewRepository;
import com.cts.grantserve.repository.IProposalRepository;
import com.cts.grantserve.dto.ReviewDto;
import com.cts.grantserve.entity.Review;
import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.exception.ReviewNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class ReviewServiceImpl implements IReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private IProposalRepository proposalRepository;

    @Autowired
    private ProposalServiceImpl proposalService;

    @Override
    public String assignReviewer(ReviewDto reviewDto) {
        log.info("Service: Assigning Reviewer ID {} to Proposal ID {}", reviewDto.reviewerId(), reviewDto.proposalId());

        Review review = new Review();
        Proposal proposal = proposalRepository.findById(reviewDto.proposalId())
                .orElseThrow(() -> {
                    log.error("Service Error: Proposal ID {} not found", reviewDto.proposalId());
                    return new RuntimeException("Proposal not found");
                });

        review.setProposal(proposal);
        review.setReviewerID(reviewDto.reviewerId());
        review.setStatus("Pending");
        review.setDate(reviewDto.date());
        review.setComments(reviewDto.comments());
        review.setScore(reviewDto.score());

        reviewRepository.save(review);
        log.info("Service: Review assigned and saved successfully");
        return "Review assigned successfully";
    }

    @Override
    public List<Review> getReviewsByReviewer(long reviewerId) {
        log.info("Service: Fetching dashboard for Reviewer ID: {}", reviewerId);
        List<Review> reviews = reviewRepository.findByReviewerID(reviewerId);

        if (reviews.isEmpty()) {
            log.warn("Service: No reviews found for Reviewer ID: {}", reviewerId);
            throw new ReviewNotFoundException("No reviews found for Reviewer ID: " + reviewerId);
        }

        log.info("Service: Successfully retrieved {} reviews", reviews.size());
        return reviews;
    }

    @Override
    public Review getReviewById(long id) {
        log.info("Service: Searching for review ID: {}", id);
        return reviewRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Service Error: Review ID {} not found", id);
                    return new ReviewNotFoundException("Review ID " + id + " not found");
                });
    }

    @Override
    public String updateReview(long id, ReviewDto reviewDto) {
        log.info("Service: Updating review ID: {}", id);
        Review existing = getReviewById(id);

        existing.setScore(reviewDto.score());
        existing.setComments(reviewDto.comments());
        existing.setStatus(reviewDto.status());
        reviewRepository.save(existing);
        proposalService.addReviewToProposal(existing.getProposal().getProposalID(),existing);
        log.info("Service: Review ID {} updated successfully", id);
        return "Review updated successfully";
    }

    @Override
    public String deleteReview(long id) {
        log.info("Service: Deleting review ID: {}", id);
        Review review = getReviewById(id);
        reviewRepository.delete(review);
        log.info("Service: Review ID {} successfully deleted", id);
        return "Review deleted successfully";
    }
}