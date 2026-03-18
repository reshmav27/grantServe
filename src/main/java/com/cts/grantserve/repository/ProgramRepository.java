package com.cts.grantserve.repository;

import com.cts.grantserve.entity.Program;
//import com.cts.grantserve.enums.ProgramStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    
    @Modifying
    @Transactional
    @Query("UPDATE Program p SET p.status = com.cts.grantserve.enums.ProgramStatus.CLOSED WHERE p.programID = :programId AND p.status = com.cts.grantserve.enums.ProgramStatus.ACTIVE")
    int updateProgramStatusToClosed(@Param("programId") Long programId);

}