package com.cts.grantserve.service;

import com.cts.grantserve.DTO.GrantApplicationDto;
import com.cts.grantserve.Repository.IGrantApplicationRepository;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.exception.GrantApplicationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class GrantApplicationServiceImpl implements IGrantApplicationService {

    @Autowired
    IGrantApplicationRepository grantApplicationDao;

    public String createApplication(GrantApplicationDto grantApplicationDto) throws GrantApplicationException {
        GrantApplication grantApplication = new GrantApplication();
        BeanUtils.copyProperties(grantApplicationDto,grantApplication);
        grantApplication.setStatus("Active");
        grantApplication.setSubmittedDate(LocalDateTime.now());
        grantApplicationDao.save(grantApplication);
        return "Created SuccessFully";

    }

    public String DeleteApplication(Long id) throws  GrantApplicationException{
        grantApplicationDao.deleteById(id);
        return "Delted SuccessFully";
    }

    public Optional<GrantApplication> getApplication(Long id) {
        return grantApplicationDao.findById(id);
    }
}
