package com.cts.grantserve.dao;

import com.cts.grantserve.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramDao extends JpaRepository<Program, Long> {
    List<Program> getAllPrograms();
    Program getProgramById(Long programId);
    boolean updateProgramStatus(Long programId, String status);
}