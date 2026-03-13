package com.cts.grantserve.service;


import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.dao.ResearcherRepository;
import com.cts.grantserve.exception.ResearcherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ResearcherService {

    @Autowired
    ResearcherRepository researcherRepository;

    public String createResearcher(Researcher researcher) throws ResearcherException {
        researcher.setStatus("Pending"); // Automatically set status
        researcherRepository.save(researcher);
        return "Researcher Registered Successfully";
    }

    public String deleteResearcher(Long id) throws ResearcherException {
        researcherRepository.deleteById(id);
        return "Researcher Deleted Successfully";
    }

    public Optional<Researcher> getResearcher(Long id) {
        return researcherRepository.findById(id);
    }
}