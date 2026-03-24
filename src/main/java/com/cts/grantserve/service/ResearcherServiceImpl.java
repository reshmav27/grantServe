package com.cts.grantserve.service;

import com.cts.grantserve.dto.ResearcherDto;
import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.exception.ResearcherException;
import com.cts.grantserve.projection.IResearcherProjection;
import com.cts.grantserve.repository.ResearcherRepository;
import com.cts.grantserve.util.ClassUtilSeparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Slf4j
@Service
public class ResearcherServiceImpl implements IResearcherService {

    @Autowired
    private ResearcherRepository researcherDAO;

    @Override
    public String UpdateResearcher(Long id, ResearcherDto researcherDto) throws ResearcherException {
        log.info("Attempting to update researcher with ID: {}", id);

        Researcher existingResearcher = researcherDAO.findById(id)
                .orElseThrow(() -> {
                    log.error("Update failed: Researcher ID {} not found", id);
                    return new ResearcherException("Researcher not found with ID: " + id, HttpStatus.NOT_FOUND);
                });

        ClassUtilSeparator.researcherRegisterUtil(researcherDto, existingResearcher);
        researcherDAO.save(existingResearcher);

        log.info("Successfully updated researcher with ID: {}", id);
        return "Researcher Updated Successfully";
    }

    @Override
    public IResearcherProjection fetchResearcher(Long id) throws ResearcherException {
        log.info("Fetching researcher projection for ID: {}", id);

        return researcherDAO.findResearcherByResearcherID(id)
                .orElseThrow(() -> {
                    log.warn("Fetch failed: No projection found for ID: {}", id);
                    return new ResearcherException("Researcher not found with ID: " + id, HttpStatus.NOT_FOUND);
                });
    }

    @Override
    public String deleteResearcher(Long id) throws ResearcherException {
        log.info("Request to delete researcher with ID: {}", id);

        if (!researcherDAO.existsById(id)) {
            log.error("Delete failed: ID {} does not exist", id);
            throw new ResearcherException("Cannot delete. ID not found: " + id, HttpStatus.NOT_FOUND);
        }

        researcherDAO.deleteById(id);
        log.info("Researcher with ID {} deleted successfully", id);
        return "Researcher Deleted Successfully";
    }

    @Override
    public Optional<Researcher> getResearcher(Long id) {
        log.debug("Internal call to getResearcher Optional for ID: {}", id);
        return researcherDAO.findById(id);
    }
}