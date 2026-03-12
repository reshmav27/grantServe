package com.cts.grantserve.globalexception;

import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.exception.GrantApplicationException;
import com.cts.grantserve.exception.ProposalException;
import com.cts.grantserve.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(ProposalException.class)
    public ResponseEntity<String> proposalExceptionHandler(ProposalException p){
        return ResponseEntity.status(p.getHttpStatus()).body(p.getMessage());
    }

    @ExceptionHandler(GrantApplicationException.class)
    public ResponseEntity<String> GrantApplicationExceptionHandler(GrantApplicationException g){
        return ResponseEntity.status(g.getHttpStatus()).body(g.getMessage());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> UserException(UserException u){
        return ResponseEntity.status(u.getHttpStatus()).body(u.getMessage());
    }
}
