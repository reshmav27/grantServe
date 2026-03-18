package com.cts.grantserve.repository;

import com.cts.grantserve.entity.User;
import com.cts.grantserve.projection.IUserProjection;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IUserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User u WHERE " +
            "(:id IS NOT NULL AND u.userID = :id)" )
    Optional<IUserProjection> finduserById(@Param("id") Long userId);
}