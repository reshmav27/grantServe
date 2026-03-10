package com.cts.grantserve.dao;

import com.cts.grantserve.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Custom query to find all reviews assigned to a specific reviewer
    List<Review> findByReviewerID(Long reviewerID);

    // Custom query to find a review by its status (e.g., "Pending")
    List<Review> findByStatus(String status);
}