package com.cts.grantserve.repository;

import com.cts.grantserve.entity.Program;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long>, JpaSpecificationExecutor<Program> {
    @Modifying
    @Transactional
    @Query("UPDATE Program p SET p.status = com.cts.grantserve.enums.ProgramStatus.CLOSED WHERE p.programID = :programId AND p.status = com.cts.grantserve.enums.ProgramStatus.ACTIVE")
    int updateProgramStatusToClosed(@Param("programId") Long programId);

    @Query("SELECT p FROM Program p WHERE p.startDate <= :now AND p.endDate >= :now")
    List<Program> findActiveApplications(@Param("now") LocalDate now);

}