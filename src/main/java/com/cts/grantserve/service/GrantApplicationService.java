package com.cts.grantserve.service;

import com.cts.grantserve.dao.IGrantApplicationDao;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.exception.GrantApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrantApplicationService {

    @Autowired
    IGrantApplicationDao grantApplicationDao;

    public String createApplication(GrantApplication grantApplication) throws GrantApplicationException {
        grantApplicationDao.save(grantApplication);
        return "Created SuccessFully";

    }

    public String DeleteApplication(int id) throws  GrantApplicationException{
        grantApplicationDao.deleteById(id);
        return "Delted SuccessFully";
    }

    public Optional<GrantApplication> getApplication(int id) {
        return grantApplicationDao.findById(id);
    }
}
