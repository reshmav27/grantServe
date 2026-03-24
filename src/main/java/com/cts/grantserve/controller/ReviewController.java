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
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private IReviewService reviewService;

    @PostMapping("/assign")
    public ResponseEntity<String> assign(@Valid @RequestBody ReviewDto reviewDto) {
        // Record accessors: reviewerId() and proposalId()
        log.info("Request: Assigning Reviewer {} to Proposal {}", reviewDto.reviewerId(), reviewDto.proposalId());
        String response = reviewService.assignReviewer(reviewDto);
        log.info("Success: Review assigned");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/dashboard/{reviewerId}")
    public ResponseEntity<List<Review>> getDashboard(@PathVariable long reviewerId) {
        log.info("Request: Dashboard data for Reviewer ID: {}", reviewerId);
        List<Review> reviews = reviewService.getReviewsByReviewer(reviewerId);
        log.info("Success: Found {} reviews for Reviewer {}", reviews.size(), reviewerId);
        return ResponseEntity.ok(reviews);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @Valid @RequestBody ReviewDto reviewDto) {
        log.info("Request: Updating Review ID: {}", id);
        String response = reviewService.updateReview(id, reviewDto);
        log.info("Success: Review ID: {} updated", id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        log.warn("Request: Deleting Review ID: {}", id);
        String response = reviewService.deleteReview(id);
        log.info("Success: Review ID: {} removed", id);
        return ResponseEntity.ok(response);
    }
}