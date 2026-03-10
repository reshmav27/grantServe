package com.cts.grantserve.dao;

import com.cts.grantserve.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    // You can add custom finders here if needed,
    // e.g., finding evaluations by result (Approved/Rejected)
}