package com.cts.grantserve.service;

import com.cts.grantserve.dto.ProgramDto;
import com.cts.grantserve.entity.Program;
import com.cts.grantserve.enums.ProgramStatus;

import java.util.List;
import java.util.Optional;

public interface IProgramService {

    String createProgram(ProgramDto programDto);

    String updateProgram(Long id, ProgramDto programDto);

    String updateProgramStatus(Long id, ProgramStatus status);

    Optional<Program> getProgram(Long id);

    List<Program> getAllPrograms();
}