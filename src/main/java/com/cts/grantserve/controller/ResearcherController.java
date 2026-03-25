package com.cts.grantserve.controller;

import com.cts.grantserve.dto.ResearcherDto;
import com.cts.grantserve.projection.IResearcherProjection;
import com.cts.grantserve.repository.ResearcherRepository;
import com.cts.grantserve.exception.ResearcherException;
import com.cts.grantserve.service.ResearcherServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j; // 1. Import Slf4j
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/researcher")
public class ResearcherController {

    @Autowired
    private ResearcherRepository researcherRepository;

    @Autowired
    private ResearcherServiceImpl researcherService;

    // 1. Update/Register a Researcher
    @PutMapping("/Update/{id}")
    public String updateResearcher(
            @PathVariable Long id,
            @Valid @RequestBody ResearcherDto researcherDto
    ) throws ResearcherException {
        log.info("REST request to update Researcher ID: {}", id);
        String response = researcherService.UpdateResearcher(id, researcherDto);
        log.info("Update successful for ID: {}", id);
        return response;
    }

    // 2. Get a Researcher by ID
    @GetMapping("/{id}")
    public IResearcherProjection getResearcher(@PathVariable Long id) throws ResearcherException {
        log.info("REST request to fetch Researcher details for ID: {}", id);
        IResearcherProjection projection = researcherService.fetchResearcher(id);
        log.debug("Successfully retrieved projection for ID: {}", id);
        return projection;
    }

    // 3. Delete a Researcher
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws ResearcherException {
        log.warn("REST request to DELETE Researcher ID: {}", id);
        String response = researcherService.deleteResearcher(id);
        log.info("Deletion confirmed for ID: {}", id);
        return response;
    }
}