package com.cts.grantserve.service;

import com.cts.grantserve.DTO.BudgetDto;
import com.cts.grantserve.DTO.ProgramDto;
import com.cts.grantserve.entity.Budget;
import com.cts.grantserve.entity.Program;
import com.cts.grantserve.enums.BudgetStatus;
import com.cts.grantserve.enums.ProgramStatus;
import com.cts.grantserve.exception.ProgramNotFoundException;
import com.cts.grantserve.exception.ProgramNotModifiableException;
import com.cts.grantserve.repository.ProgramRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
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
    public ProgramDto createProgram(ProgramDto programDto) {
        Program program = new Program();
        BeanUtils.copyProperties(programDto, program);

        Program savedProgram = programRepository.save(program);

        if (savedProgram.getStatus() == ProgramStatus.ACTIVE) {
            initializeProgramBudget(savedProgram);
        }

        return convertToDto(savedProgram);
    }

    @Transactional
    @Override
    public String updateProgram(ProgramDto programDto) {
        Long id = programDto.programID();

        Program existingProgram = programRepository.findById(id)
                .orElseThrow(() -> new ProgramNotFoundException("Program not found with id: " + id));

        if (existingProgram.getStatus() != ProgramStatus.DRAFT) {
            throw new ProgramNotModifiableException("Update failed. Only DRAFT programs can be modified.");
        }

        Program updatedProgram = new Program();
        BeanUtils.copyProperties(programDto, updatedProgram);

        programRepository.save(updatedProgram);

        if (programDto.status() == ProgramStatus.ACTIVE) {
            Optional<Budget> existingBudget = budgetService.getBudgetByProgram(id);
            if (existingBudget.isEmpty()) {
                initializeProgramBudget(updatedProgram);
            }
        }

        return "Program updated successfully with status: " + programDto.status() +
            (programDto.status() == ProgramStatus.ACTIVE ? " and budget activated." : ".");
    }

    @Transactional
    @Override
    public String updateProgramStatusToClosed(Long id) {
        int rowsAffected = programRepository.updateProgramStatusToClosed(id);
        if (rowsAffected == 0) {
            throw new ProgramNotModifiableException("Cannot close program. Either the program ID is Invalid or the program is already in CLOSED status.");
        }
        String res = budgetService.updateBudgetStatusToClosed(id);
        return "Program status updated to CLOSED successfully. " + res;
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
                BudgetStatus.ACTIVE,
                program.getProgramID()
        );

        budgetService.createBudget(budgetDto);
    }

    private ProgramDto convertToDto(Program program) {
        return new ProgramDto(
                program.getProgramID(),
                program.getTitle(),
                program.getDescription(),
                program.getStartDate(),
                program.getEndDate(),
                program.getBudget(),
                program.getStatus()
        );
    }

}