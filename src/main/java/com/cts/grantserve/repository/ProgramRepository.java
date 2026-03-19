package com.cts.grantserve.repository;

import com.cts.grantserve.entity.Program;
import com.cts.grantserve.enums.ProgramStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    default List<Program> getAllPrograms() {
        return findAll();
    }

    default Program getProgramById(Long programId) {
        return findById(programId).orElse(null);
    }

    @Modifying
    @Transactional
    @Query("UPDATE Program p SET p.title = :title, p.description = :description, " +
           "p.startDate = :startDate, p.endDate = :endDate, p.budget = :budget, p.status = :status " +
           "WHERE p.programID = :id AND p.status = com.cts.grantserve.enums.ProgramStatus.DRAFT")
    int updateProgramDetailsIfDraft(
        @Param("id") Long id, 
        @Param("title") String title, 
        @Param("description") String description,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("budget") Double budget,
        @Param("status") ProgramStatus status
    );

    @Modifying
    @Transactional
    @Query("UPDATE Program p SET p.status = :status WHERE p.programID = :programId AND p.status = com.cts.grantserve.enums.ProgramStatus.DRAFT")
    int updateProgramStatus(@Param("programId") Long programId, @Param("status") ProgramStatus status);
    
    @Modifying
    @Transactional
    @Query("UPDATE Program p SET p.status = com.cts.grantserve.enums.ProgramStatus.CLOSED WHERE p.programID = :programId AND p.status = com.cts.grantserve.enums.ProgramStatus.ACTIVE")
    int updateProgramStatusToClosed(@Param("programId") Long programId);
}