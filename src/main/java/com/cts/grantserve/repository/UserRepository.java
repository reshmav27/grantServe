package com.cts.grantserve.repository;

import com.cts.grantserve.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
