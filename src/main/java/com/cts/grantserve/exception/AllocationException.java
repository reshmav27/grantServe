package com.cts.grantserve.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class AllocationException extends RuntimeException {
    private HttpStatus httpStatus;

    public AllocationException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}