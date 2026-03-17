package com.cts.grantserve.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class DisbursementException extends RuntimeException {
    private HttpStatus httpStatus;

    public DisbursementException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}