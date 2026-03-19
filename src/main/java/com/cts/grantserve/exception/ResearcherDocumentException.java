package com.cts.grantserve.exception;

import org.springframework.http.HttpStatus;

public class ResearcherDocumentException extends RuntimeException {
    public ResearcherDocumentException(String message, HttpStatus notFound)
    {
        super(message);
    }
}
