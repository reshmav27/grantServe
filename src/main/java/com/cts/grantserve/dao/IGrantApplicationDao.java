package com.cts.grantserve.dao;

import com.cts.grantserve.entity.GrantApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGrantApplicationDao extends JpaRepository<GrantApplication,Integer> {
}
