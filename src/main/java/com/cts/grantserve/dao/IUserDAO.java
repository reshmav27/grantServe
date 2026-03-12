package com.cts.grantserve.dao;

import com.cts.grantserve.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IUserDAO extends JpaRepository<User,Long> {
}
