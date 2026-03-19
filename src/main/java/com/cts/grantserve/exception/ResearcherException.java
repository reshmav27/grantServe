package com.cts.grantserve.exception;

import org.springframework.http.HttpStatus;

public class ResearcherException extends RuntimeException {
    public ResearcherException(String message, HttpStatus notFound) {
        super(message);
    }
}
