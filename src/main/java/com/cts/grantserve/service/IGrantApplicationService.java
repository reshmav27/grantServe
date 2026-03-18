package com.cts.grantserve.service;

import com.cts.grantserve.dto.GrantApplicationDto;
import com.cts.grantserve.entity.GrantApplication;

import java.util.Optional;

public interface IGrantApplicationService {

     String createApplication(GrantApplicationDto grantApplication);

     String DeleteApplication(Long id);

     Optional<GrantApplication> getApplication(Long id) ;
}
