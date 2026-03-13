package com.cts.grantserve.dao;

import com.cts.grantserve.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewDao extends JpaRepository<Review, Long> {

    List<Review> findByReviewerID(long reviewerId);
}