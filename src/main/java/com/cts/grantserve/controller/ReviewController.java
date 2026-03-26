package com.cts.grantserve.controller;

import com.cts.grantserve.dto.ReviewDto;
import com.cts.grantserve.entity.Review;
import com.cts.grantserve.service.IReviewService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("review") // Added /api prefix for consistency with Evaluation module
public class ReviewController {

    @Autowired
    private IReviewService reviewService;

    // POST: Assign a new reviewer to a proposal
    @PostMapping("/assign")
    public ResponseEntity<String> assign(@Valid @RequestBody ReviewDto reviewDto) {
        log.info("Controller: Assigning Reviewer ID {} to Proposal ID {}",
                reviewDto.reviewerId(), reviewDto.proposalId());

        String response = reviewService.assignReviewer(reviewDto);

        log.info("Controller: Review successfully assigned");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET: Reviewer Dashboard - Fetch all reviews for a specific user
    @GetMapping("/dashboard/{reviewerId}")
    public ResponseEntity<List<Review>> getDashboard(@PathVariable long reviewerId) {
        log.info("Controller: Fetching dashboard for Reviewer ID: {}", reviewerId);

        List<Review> reviews = reviewService.getReviewsByReviewer(reviewerId);

        log.info("Controller: Successfully found {} reviews", reviews.size());
        return ResponseEntity.ok(reviews);
    }

    // GET: Fetch a specific review by its ID (Added for complete CRUD)
    @GetMapping("/{id}")
    public ResponseEntity<Review> getById(@PathVariable long id) {
        log.info("Controller: Fetching individual Review ID: {}", id);
        Review review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    // PUT: Update score, comments, or status (e.g., from PENDING to REVIEWED)
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @Valid @RequestBody ReviewDto reviewDto) {
        log.info("Controller: Updating Review ID: {}", id);

        String response = reviewService.updateReview(id, reviewDto);

        log.info("Controller: Review ID {} updated successfully", id);
        return ResponseEntity.ok(response);
    }

    // DELETE: Remove a review assignment
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        log.warn("Controller: Deleting Review record ID: {}", id);

        String response = reviewService.deleteReview(id);

        log.info("Controller: Review record ID {} deleted", id);
        return ResponseEntity.ok(response);
    }
}