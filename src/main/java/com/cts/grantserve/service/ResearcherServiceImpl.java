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
    private ResearcherRepository researcherDAO;

    @Override
    public String UpdateResearcher(Long id,ResearcherDto researcherDto) throws ResearcherException {
        Researcher existingResearcher = researcherDAO.findById(id)
                .orElseThrow(() -> new ResearcherException("Researcher not found with ID: " + id,HttpStatus.NOT_FOUND));
        ClassUtilSeparator.researcherRegisterUtil(researcherDto,existingResearcher);
        researcherDAO.save(existingResearcher);
        return "Researcher Updated Successfully";
    }

    @Override
    public IResearcherProjection fetchResearcher(Long id) throws ResearcherException {
        // This resolves the error you saw in the Controller
        return researcherDAO.findResearcherByResearcherID(id)
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
    public Optional<Researcher> getResearcher(Long id) {
        return researcherDAO.findById(id);
    }
}