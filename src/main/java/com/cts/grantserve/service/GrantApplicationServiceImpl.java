package com.cts.grantserve.service;

import com.cts.grantserve.dto.GrantApplicationDto;
import com.cts.grantserve.entity.Disbursement;
import com.cts.grantserve.entity.Program;
import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.exception.ProgramNotFoundException;
import com.cts.grantserve.exception.ProposalException;
import com.cts.grantserve.exception.ResearcherException;
import com.cts.grantserve.repository.IGrantApplicationRepository;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.exception.GrantApplicationException;
import com.cts.grantserve.repository.ProgramRepository;
import com.cts.grantserve.repository.ResearcherRepository;
import com.cts.grantserve.specification.GrantApplicationSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.cts.grantserve.specification.GrantApplicationSpecification.hasName;


@Slf4j
@Service
public class GrantApplicationServiceImpl implements IGrantApplicationService {

    @Autowired
    IGrantApplicationRepository grantApplicationDao;

    @Autowired
    ProgramRepository programRepository;

    @Autowired
    ResearcherRepository researcherRepository;

    public String createApplication(GrantApplicationDto grantApplicationDto) throws GrantApplicationException {
        GrantApplication grantApplication = new GrantApplication();
        BeanUtils.copyProperties(grantApplicationDto,grantApplication);
        grantApplication.setStatus("Active");
        grantApplication.setSubmittedDate(LocalDate.now());

        Program program = programRepository.findById(grantApplicationDto.programID())
                .orElseThrow(() -> new ProgramNotFoundException("Program Not found"));
        grantApplication.setProgram(program);

        Researcher researcher = researcherRepository.findById(grantApplicationDto.researcherID())
                        .orElseThrow(()->new ResearcherException("Researcher Not found",HttpStatus.NOT_FOUND));
        grantApplication.setResearcher(researcher);

        grantApplicationDao.save(grantApplication);
        return "Created SuccessFully";

    }

    public String DeleteApplication(Long id) throws  GrantApplicationException{
        if (!grantApplicationDao.existsById(id)) {
            throw new GrantApplicationException("Delete failed: No application found with ID " + id,HttpStatus.NOT_FOUND);
        }
        grantApplicationDao.deleteById(id);
        return "Deleted SuccessFully";
    }

    public GrantApplication getApplication(Long id) throws GrantApplicationException {
        return grantApplicationDao.findById(id)
                .orElseThrow(() -> new GrantApplicationException("Application not found for ID: " + id,HttpStatus.NOT_FOUND));
    }

    public List<GrantApplication> search(Long id, String name) {
        return grantApplicationDao.findAll(
                Specification.where(GrantApplicationSpecification.hasId(id))
                        .or(GrantApplicationSpecification.hasName(name))
        );
    }
    @Override
    public List<GrantApplication> FetchGrantApplication(Long id) throws GrantApplicationException {
        return grantApplicationDao.findByResearcher_ResearcherID(id)
                .orElseThrow(()->new GrantApplicationException("No Grant Applications found for the user "+id,HttpStatus.NOT_FOUND));
    }

    @Override
    public Long getuserApplicationCount(Long id) throws  GrantApplicationException{
        return grantApplicationDao.countByResearcher_ResearcherID(id);
    }

    @Override
    public Optional<List<GrantApplication>> fetchProgramGrantApplications(Long programID) throws GrantApplicationException{
        return grantApplicationDao.findByProgram_ProgramID(programID);
    }


}
