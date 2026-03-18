package com.cts.grantserve.service;

import com.cts.grantserve.dto.ResearcherDto;
import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.exception.ResearcherException;
import com.cts.grantserve.projection.IResearcherProjection;
import com.cts.grantserve.repository.ResearcherRepository;
import com.cts.grantserve.util.ClassUtilSeparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ResearcherServiceImpl implements IResearcherService {

    @Autowired
    private ResearcherRepository researcherDAO;

    @Override
    public String createResearcher(ResearcherDto researcherDto) throws ResearcherException {
        // Using your Util pattern for cleaner mapping
        Researcher researcher = ClassUtilSeparator.researcherRegisterUtil(researcherDto);
        researcher.setStatus("Active");
        researcherDAO.save(researcher);
        return "Researcher Registered Successfully";
    }

    @Override
    public IResearcherProjection fetchResearcher(Long id) throws ResearcherException {
        // Matches your fetchUser pattern using Projections and custom Exception
        return researcherDAO.findResearcherById(id)
                .orElseThrow(() -> new ResearcherException("Researcher not found with ID: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public String deleteResearcher(Long id) throws ResearcherException {
        if (!researcherDAO.existsById(id)) {
            throw new ResearcherException("Cannot delete. ID not found: " + id, HttpStatus.NOT_FOUND);
        }
        researcherDAO.deleteById(id);
        return "Researcher Deleted Successfully";
    }

    @Override
    public String updateResearcher(Long id, ResearcherDto researcherDto) throws ResearcherException {
        Researcher existingResearcher = researcherDAO.findById(id)
                .orElseThrow(() -> new ResearcherException("Update failed. ID not found: " + id, HttpStatus.NOT_FOUND));

        ClassUtilSeparator.researcherUpdateUtil(researcherDto, existingResearcher);
        researcherDAO.save(existingResearcher);
        return "Researcher Updated Successfully";
    }
}