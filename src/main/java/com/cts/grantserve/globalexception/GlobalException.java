package com.cts.grantserve.globalexception;

import com.cts.grantserve.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@ControllerAdvice
@Slf4j
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

        log.error("Validation failed: {}", errors);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler({ ProgramNotFoundException.class, BudgetNotFoundException.class })
    public ResponseEntity<String> handleNotFoundException(RuntimeException ex) {
        log.error("Resource not found exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ProgramNotModifiableException.class)
    public ResponseEntity<String> handleProgramNotModifiableException(ProgramNotModifiableException ex) {
        log.warn("Business logic violation: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> handleInsufficientBudgetException(InsufficientFundsException ex) {
        log.error("Financial error - Insufficient funds: {}", ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }

    @ExceptionHandler(DisbursementException.class)
    public ResponseEntity<String> disbursementExceptionHandler(DisbursementException d) {
        return ResponseEntity.status(d.getHttpStatus()).body(d.getMessage());
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<String> paymentExceptionHandler(PaymentException p) {
        return ResponseEntity.status(p.getHttpStatus()).body(p.getMessage());
    }

    @ExceptionHandler(BudgetClosedException.class)
    public ResponseEntity<String> handleBudgetClosedException(BudgetClosedException ex) {
        log.warn("Attempted action on closed budget: {}", ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }


    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<Object> handleReviewNotFound(ReviewNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EvaluationNotFoundException.class)
    public ResponseEntity<Object> handleEvaluationNotFound(EvaluationNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "An unexpected error occurred");
        body.put("error", ex.getMessage()); // This helps you debug in Postman!

        log.error("Unexpected Error: ", ex);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());

        // Check if it's a unique constraint violation
        String message = "Database error: This record (likely email) already exists.";
        if (ex.getMostSpecificCause().getMessage().contains("user.email")) {
            message = "The email address is already in use. Please use a different one.";
        }

        body.put("message", message);
        log.error("Data Integrity Violation: {}", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT); // 409 Conflict is best for duplicates
    }

}