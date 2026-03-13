package com.cts.grantserve.controller;

import com.cts.grantserve.entity.Review;
import com.cts.grantserve.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    // For the Dashboard: Get all reviews assigned to a specific Reviewer
    @GetMapping("/getAssignedReviews/{reviewerId}")
    public ResponseEntity<List<Review>> getAssignedReviews(@PathVariable long reviewerId) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getReviewsByReviewer(reviewerId));
    }

    // Get specific review details by ID
    @GetMapping("/getReview/{id}")
    public ResponseEntity<Review> getReview(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getReviewById(id));
    }
}