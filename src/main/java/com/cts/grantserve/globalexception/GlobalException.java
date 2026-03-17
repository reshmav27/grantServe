package com.cts.grantserve.globalexception;

import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler({ProgramNotFoundException.class, BudgetNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ProgramNotModifiableException.class)
    public ResponseEntity<String> handleProgramNotModifiableException(ProgramNotModifiableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> handleInsufficientBudgetException(InsufficientFundsException ex) {
        // Returns a 400 Bad Request with the custom message
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }

    @ExceptionHandler(AuditException.class)
    public ResponseEntity<String> proposalExceptionHandler(AuditException p){
        return ResponseEntity.status(p.getHttpStatus()).body(p.getMessage());
    }

    @ExceptionHandler(ComplianceRecordException.class)
    public ResponseEntity<String> GrantApplicationExceptionHandler(ComplianceRecordException g){
        return ResponseEntity.status(g.getHttpStatus()).body(g.getMessage());
    }
}