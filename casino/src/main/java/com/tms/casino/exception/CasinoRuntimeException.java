package com.tms.casino.exception;

import org.springframework.http.HttpStatus;

public abstract class CasinoRuntimeException extends RuntimeException {
    private final HttpStatus httpStatus;

    public CasinoRuntimeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public CasinoRuntimeException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public CasinoRuntimeException() {
        this("Invalid operation", HttpStatus.BAD_REQUEST);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}