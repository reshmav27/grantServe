package com.cts.grantserve.repository;

import com.cts.grantserve.entity.GrantApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGrantApplicationRepository extends JpaRepository<GrantApplication,Long> {
}
