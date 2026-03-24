package com.cts.grantserve.service;

import com.cts.grantserve.dto.BudgetDto;
import com.cts.grantserve.dto.ProgramDto;
import com.cts.grantserve.entity.Budget;
import com.cts.grantserve.entity.Program;
import com.cts.grantserve.enums.BudgetStatus;
import com.cts.grantserve.enums.ProgramStatus;
import com.cts.grantserve.exception.ProgramNotFoundException;
import com.cts.grantserve.exception.ProgramNotModifiableException;
import com.cts.grantserve.projection.IProgramProjection;
import com.cts.grantserve.repository.ProgramRepository;
import com.cts.grantserve.specification.ProgramSpecification;
import com.cts.grantserve.util.ClassUtilSeparator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProgramServiceImpl implements IProgramService {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private IBudgetService budgetService;

    @Transactional
    @Override
    public ProgramDto createProgram(ProgramDto programDto) {
        log.debug("Mapping DTO to Entity for program: {}", programDto.title());
        Program program = ClassUtilSeparator.programUtil(programDto);

        Program savedProgram = programRepository.save(program);

        if (savedProgram.getStatus() == ProgramStatus.ACTIVE) {
            log.info("Program {} is ACTIVE. Initializing budget record.", savedProgram.getProgramID());
            initializeProgramBudget(savedProgram);
        }

        return convertToDto(savedProgram);
    }

    @Transactional
    @Override
    public String updateProgram(ProgramDto programDto) {
        Long id = programDto.programID();
        log.info("Attempting to update program with ID: {}", id);

        Program existingProgram = programRepository.findById(id)
                .orElseThrow(() -> new ProgramNotFoundException("Program not found with id: " + id));

        if (existingProgram.getStatus() != ProgramStatus.DRAFT) {
            throw new ProgramNotModifiableException("Update failed. Only DRAFT programs can be modified.");
        }

        Program updatedProgram = ClassUtilSeparator.programUtil(programDto);

        programRepository.save(updatedProgram);

        if (programDto.status() == ProgramStatus.ACTIVE) {
            log.info("Program {} updated to ACTIVE. Checking for budget initialization.", id);
            Optional<Budget> existingBudget = budgetService.getBudgetByProgram(id);
            if (existingBudget.isEmpty()) {
                initializeProgramBudget(updatedProgram);
            }
        }

        log.info("Program {} successfully updated to status: {}", id, programDto.status());
        return "Program updated successfully with status: " + programDto.status() +
            (programDto.status() == ProgramStatus.ACTIVE ? " and budget activated." : ".");
    }

    @Transactional
    @Override
    public String updateProgramStatusToClosed(Long id) {
        log.info("Request received to CLOSE program ID: {}", id);

        int rowsAffected = programRepository.updateProgramStatusToClosed(id);

        if (rowsAffected == 0) {
            throw new ProgramNotModifiableException("Cannot close program. Either the program ID is Invalid or the program is already in CLOSED status.");
        }

        log.info("Program ID: {} marked as CLOSED in repository. Proceeding to close associated budget.", id);

        String res = budgetService.updateBudgetStatusToClosed(id);

        log.info("Program and Budget for ID: {} successfully CLOSED.", id);
        return "Program status updated to CLOSED successfully. " + res;
    }

    @Override
    public Optional<IProgramProjection> getProgram(Long id) {
        return programRepository.findProjectedByProgramID(id);
    }

    @Override
    public List<IProgramProjection> getAllPrograms() {
        return programRepository.findAllProjectedBy();
    }

    @Cacheable(value = "activeGrants", key = "#now")
    public List<IProgramProjection> getActiveApplications(LocalDate now) {
        return programRepository.findActiveApplications(now);
    }

    @Override
    public List<IProgramProjection> searchProgram(String title, Long id, boolean isManager) {
        if (isManager) {
            return programRepository.findAllProjectedBy(
                Specification.where(ProgramSpecification.hasName(title))
                    .and(ProgramSpecification.hasId(id))
            );
        }
        return  programRepository.findAllProjectedBy(
            Specification.where(ProgramSpecification.hasName(title))
                .and(ProgramSpecification.hasId(id))
                .and(ProgramSpecification.hasNotStatus(ProgramStatus.DRAFT))
        );
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