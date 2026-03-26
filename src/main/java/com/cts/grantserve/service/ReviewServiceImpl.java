package com.cts.grantserve.service;

import com.cts.grantserve.dto.ReviewDto;
import com.cts.grantserve.entity.Review;
import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.entity.User;
import com.cts.grantserve.exception.ReviewNotFoundException;
import com.cts.grantserve.repository.ReviewRepository;
import com.cts.grantserve.repository.IProposalRepository;
import com.cts.grantserve.repository.UserRepository; // Added this
import com.cts.grantserve.util.ClassUtilSeparator;
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
    private UserRepository userRepository; // Added to fetch the User object

    @Override
    public String assignReviewer(ReviewDto reviewDto) {
        log.info("Service: Assigning Reviewer ID {} to Proposal ID {}", reviewDto.reviewerId(), reviewDto.proposalId());

        // 1. Fetch Proposal
        Proposal proposal = proposalRepository.findById(reviewDto.proposalId())
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        // 2. Fetch User (Reviewer)
        User reviewer = userRepository.findById(reviewDto.reviewerId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + reviewDto.reviewerId()));

        // 3. Check Role (Optional but recommended)
        if (!"REVIEWER".equalsIgnoreCase(reviewer.getRole())) {
            throw new RuntimeException("Selected user is not a Reviewer");
        }

        // 4. Use Util to map Entity
        Review review = ClassUtilSeparator.reviewUtil(reviewDto, proposal, reviewer);

        reviewRepository.save(review);
        log.info("Service: Review assigned successfully");
        return "Review assigned successfully";
    }

    @Override
    public List<Review> getReviewsByReviewer(long reviewerId) {
        log.info("Service: Fetching reviews for Reviewer ID: {}", reviewerId);

        // Updated to match the repository method for User-linked mapping
        List<Review> reviews = reviewRepository.findByReviewer_UserID(reviewerId);

        if (reviews.isEmpty()) {
            log.warn("Service: No reviews found for Reviewer ID: {}", reviewerId);
            throw new ReviewNotFoundException("No reviews found for Reviewer ID: " + reviewerId);
        }

        return reviews;
    }

    @Override
    public Review getReviewById(long id) {
        log.info("Service: Searching for review ID: {}", id);
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review ID " + id + " not found"));
    }

    @Override
    public String updateReview(long id, ReviewDto reviewDto) {
        log.info("Service: Updating review ID: {}", id);
        Review existing = getReviewById(id);

        // Update fields from DTO
        existing.setScore(reviewDto.score());
        existing.setComments(reviewDto.comments());
        existing.setStatus(reviewDto.status()); // Sets the Enum value
        existing.setDate(reviewDto.date());

        reviewRepository.save(existing);
        log.info("Service: Review ID {} updated successfully", id);
        return "Review updated successfully";
    }

    @Override
    public String deleteReview(long id) {
        log.info("Service: Deleting review ID: {}", id);
        Review review = getReviewById(id);
        reviewRepository.delete(review);
        return "Review deleted successfully";
    }
}