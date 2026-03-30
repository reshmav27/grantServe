package com.cts.grantserve.repository;

import com.cts.grantserve.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // This tells JPA: "Go into the 'reviewer' object and find the 'userID'"
    List<Review> findByReviewer_UserID(Long userID);
}