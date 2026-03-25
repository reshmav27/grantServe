package com.cts.grantserve.service;

import com.cts.grantserve.dto.GrantApplicationDto;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.exception.GrantApplicationException;

import java.util.List;
import java.util.Optional;

public interface IGrantApplicationService {

     String createApplication(GrantApplicationDto grantApplication);

     String DeleteApplication(Long id);

     public GrantApplication getApplication(Long id) throws GrantApplicationException;

     public List<GrantApplication> search(Long id, String title);

    List<GrantApplication> FetchGrantApplication(Long id);

    Optional<List<GrantApplication>> fetchProgramGrantApplications(Long programID) throws GrantApplicationException;
}
