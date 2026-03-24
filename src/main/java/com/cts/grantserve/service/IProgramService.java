package com.cts.grantserve.service;

import com.cts.grantserve.dto.ProgramDto;
import com.cts.grantserve.entity.Program;
import com.cts.grantserve.enums.ProgramStatus;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IProgramService {

    @Transactional
    ProgramDto createProgram(ProgramDto programDto);

    @Transactional
    String updateProgram(ProgramDto programDto);

    @Transactional
    String updateProgramStatusToClosed(Long id);

    Optional<Program> getProgram(Long id);

    List<Program> getAllPrograms();

    public List<Program> getActiveApplications(LocalDate now);

    List<Program> searchprogram(String title,Long id);
}