package com.cts.grantserve.service;

import com.cts.grantserve.dto.ResearcherDto;
import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.exception.ResearcherException;
import com.cts.grantserve.projection.IResearcherProjection;
import com.cts.grantserve.repository.ResearcherRepository;
import com.cts.grantserve.util.ClassUtilSeparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import com.cts.grantserve.projection.IResearcherProjection;

import java.util.Optional;

@Service
public class ResearcherServiceImpl implements IResearcherService {

    @Autowired
    private ResearcherRepository researcherRepository ;

    @Override
    public String createResearcher(ResearcherDto researcherDto) throws ResearcherException {
        Researcher researcher = ClassUtilSeparator.researcherRegisterUtil(researcherDto);
        researcher.setStatus("Active");
        researcherRepository .save(researcher);
        return "Researcher Registered Successfully";
    }

    @Override
    public IResearcherProjection fetchResearcher(Long id) throws ResearcherException {
        // This resolves the error you saw in the Controller
        return researcherRepository .findResearcherByResearcherID(id)
                .orElseThrow(() -> new ResearcherException("Researcher not found with ID: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public String deleteResearcher(Long id) throws ResearcherException {
        if (!researcherRepository .existsById(id)) {
            throw new ResearcherException("Cannot delete. ID not found: " + id, HttpStatus.NOT_FOUND);
        }
        researcherRepository .deleteById(id);
        return "Researcher Deleted Successfully";
    }

    @Override
    public Optional<Researcher> getResearcher(Long id) {
        return researcherRepository .findById(id);
    }
}