package com.cts.grantserve.service;

import com.cts.grantserve.dto.ReviewDto;
import com.cts.grantserve.entity.Review;
import java.util.List;

public interface IReviewService {
    String assignReviewer(ReviewDto reviewDto);
    List<Review> getReviewsByReviewer(long reviewerId);
    Review getReviewById(long id);
    String updateReview(long id, ReviewDto reviewDto);
    String deleteReview(long id);
}