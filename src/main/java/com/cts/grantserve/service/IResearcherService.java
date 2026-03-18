package com.cts.grantserve.service;

import com.cts.grantserve.dto.ResearcherDto;
import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.exception.ResearcherException;
import com.cts.grantserve.projection.IResearcherProjection;

import java.util.Optional;

public interface IResearcherService {
    String createResearcher(ResearcherDto researcherDto) throws ResearcherException;
    IResearcherProjection fetchResearcher(Long id) throws ResearcherException;
    String deleteResearcher(Long id) throws ResearcherException;

    Optional<Researcher> getResearcher(Long id);
}
