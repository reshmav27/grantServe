package com.cts.grantserve.repository;

import com.cts.grantserve.entity.Budget;
import com.cts.grantserve.projection.IBudgetProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    @Query("SELECT b FROM Budget b")
    List<IBudgetProjection> findAllProjectedBy();

    Optional<IBudgetProjection> findProjectedByBudgetID(Long id);

    Optional<Budget> findByProgramProgramID(Long programId);

    @Modifying
    @Transactional
    @Query("UPDATE Budget b SET b.status = com.cts.grantserve.enums.BudgetStatus.CLOSED " +
            "WHERE b.program.programID = :programId AND b.status = com.cts.grantserve.enums.BudgetStatus.ACTIVE")
    int updateBudgetStatusToClosed(@Param("programId") Long programId);
}