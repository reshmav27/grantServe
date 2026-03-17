package com.cts.grantserve.service;

import com.cts.grantserve.DTO.BudgetDto;
import com.cts.grantserve.DTO.ProgramDto;
import com.cts.grantserve.entity.Budget;
import com.cts.grantserve.entity.Program;
import com.cts.grantserve.enums.ProgramStatus;
import com.cts.grantserve.exception.ProgramNotFoundException;
import com.cts.grantserve.exception.ProgramNotModifiableException;
import com.cts.grantserve.repository.ProgramRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramServiceImpl implements IProgramService {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private IBudgetService budgetService;

    @Transactional
    @Override
    public String createProgram(ProgramDto programDto) {
        Program program = new Program();
        program.setTitle(programDto.title());
        program.setDescription(programDto.description());
        program.setStartDate(programDto.startDate());
        program.setEndDate(programDto.endDate());
        program.setBudget(programDto.budget());
        program.setStatus(programDto.status());

        Program savedProgram = programRepository.save(program);

        if (savedProgram.getStatus() == ProgramStatus.ACTIVE) {
            initializeProgramBudget(savedProgram);
        }

        return "Program created successfully with status: " + savedProgram.getStatus() +
                (savedProgram.getStatus() == ProgramStatus.ACTIVE ? " and budget initialized" : "");
    }

    @Transactional
    @Override
    public String updateProgram(Long id, ProgramDto programDto) {
        Program existingProgram = programRepository.findById(id)
                .orElseThrow(() -> new ProgramNotFoundException("Program not found with id: " + id));

        if (existingProgram.getStatus() != ProgramStatus.DRAFT) {
            throw new ProgramNotModifiableException("Update failed. Only DRAFT programs can be modified.");
        }

        int rowsAffected = programRepository.updateProgramDetailsIfDraft(
                id,
                programDto.title(),
                programDto.description(),
                programDto.startDate(),
                programDto.endDate(),
                programDto.budget(),
                programDto.status()
        );

        if (rowsAffected == 0) {
            throw new ProgramNotModifiableException("Cannot update; program state has changed.");
        }

        if (programDto.status() == ProgramStatus.ACTIVE) {
            Optional<Budget> existingBudget = budgetService.getBudgetByProgram(id);
            if (existingBudget.isEmpty()) {
                existingProgram.setStatus(programDto.status());
                initializeProgramBudget(existingProgram);
            }
        }

        return "Program updated successfully with status: " + programDto.status();
    }

    @Transactional
    @Override
    public String updateProgramStatus(Long id, ProgramStatus status) {
        if (status == ProgramStatus.DRAFT) {
            throw new ProgramNotModifiableException("Cannot revert to DRAFT status.");
        }

        if (status == ProgramStatus.CLOSED) {
            int rowsAffected = programRepository.updateProgramStatusToClosed(id);
            if (rowsAffected == 0) {
                throw new ProgramNotModifiableException("Cannot close program. It is not in ACTIVE status.");
            }
            return "Program status updated to CLOSED successfully.";
        }

        int rowsAffected = programRepository.updateProgramStatus(id, status);

        if (rowsAffected == 0) {
            Program existing = programRepository.findById(id)
                    .orElseThrow(() -> new ProgramNotFoundException("Program not found with id: " + id));
            throw new ProgramNotModifiableException("Status can only be updated from DRAFT. Current status: " + existing.getStatus());
        }

        if (status == ProgramStatus.ACTIVE) {
            Optional<Budget> existingBudget = budgetService.getBudgetByProgram(id);
            if (existingBudget.isEmpty()) {
                Program updatedProgram = programRepository.findById(id)
                        .orElseThrow(() -> new ProgramNotFoundException("Program not found with id: " + id));
                initializeProgramBudget(updatedProgram);
            }
        }

        return "Program status updated to " + status + " successfully.";
    }

    @Override
    public Optional<Program> getProgram(Long id) {
        return programRepository.findById(id);
    }

    @Override
    public List<Program> getAllPrograms() {
        return programRepository.findAll();
    }

    private void initializeProgramBudget(Program program) {
        BudgetDto budgetDto = new BudgetDto(
                null,
                program.getBudget(),
                0.0,
                program.getBudget(),
                program.getStatus().name(),
                program.getProgramID()
        );

        budgetService.createBudget(budgetDto);
    }

    private Program convertToEntity(Long id, ProgramDto dto) {
        Program program = new Program();
        program.setProgramID(id);
        program.setTitle(dto.title());
        program.setDescription(dto.description());
        program.setStartDate(dto.startDate());
        program.setEndDate(dto.endDate());
        program.setBudget(dto.budget());
        program.setStatus(dto.status());
        return program;
    }
}