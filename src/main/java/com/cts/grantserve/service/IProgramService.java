package com.cts.grantserve.service;

import com.cts.grantserve.dto.ProgramDto;
import com.cts.grantserve.entity.Program;
import com.cts.grantserve.projection.IProgramProjection;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IProgramService {

    @Transactional
    ProgramDto createProgram(ProgramDto programDto);

    @Transactional
    String updateProgram(ProgramDto programDto);

    @Transactional
    String updateProgramStatusToClosed(Long id);

    Optional<IProgramProjection> getProgram(Long id);

    List<IProgramProjection> getAllPrograms();

    List<IProgramProjection> getActiveApplications(LocalDate now);

    List<IProgramProjection> searchProgram(String title, Long id, boolean isManager);
}