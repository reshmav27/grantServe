package com.cts.grantserve.controller;

import com.cts.grantserve.dto.ResearcherDto;
import com.cts.grantserve.repository.ResearcherRepository;
import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.exception.ResearcherException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/researcher")
public class ResearcherController {

    @Autowired
    ResearcherRepository researcherRepository; // Or use your Service if preferred

    @Autowired
    ResearcherService researcherService;

    // 1. Create/Register a Researcher
    @PostMapping("/register")
    public String register(@Valid @RequestBody ResearcherDto researcherDto) throws ResearcherException {
        return researcherService.createResearcher(researcherDto);
    }

    // 2. Get a Researcher by ID
    @GetMapping("/{id}")
    public Optional<Researcher> getResearcher(@PathVariable Long id) {
        return researcherService.getResearcher(id);
    }

    // 3. Delete a Researcher
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws ResearcherException {
        return researcherService.deleteResearcher(id);
    }
}
