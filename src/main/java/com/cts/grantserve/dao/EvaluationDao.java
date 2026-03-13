package com.cts.grantserve.dao;

import com.cts.grantserve.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationDao extends JpaRepository<Evaluation, Long> {

}