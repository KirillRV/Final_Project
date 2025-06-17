package com.tms.casino.exception;

import org.springframework.http.HttpStatus;

public class CasinoRuntimeException extends RuntimeException {

    private final HttpStatus httpStatus;

    public CasinoRuntimeException(String errorCode, String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
