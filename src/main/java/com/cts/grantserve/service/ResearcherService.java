package com.cts.grantserve.service;


import com.cts.grantserve.DTO.ResearcherDto;
import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.repository.ResearcherRepository;
import com.cts.grantserve.exception.ResearcherException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ResearcherService {

    @Autowired
    ResearcherRepository researcherRepository;

    public String createResearcher(ResearcherDto researcherDto) throws ResearcherException {
         // Automatically set status
        Researcher researcher = new Researcher();
        BeanUtils.copyProperties(researcherDto,researcher);
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