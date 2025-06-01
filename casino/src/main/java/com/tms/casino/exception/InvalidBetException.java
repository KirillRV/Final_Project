package com.tms.casino.exception;

import org.springframework.http.HttpStatus;

public class InvalidBetException extends CasinoRuntimeException {

    public InvalidBetException() {
        super("Invalid bet parameters");
    }

    public InvalidBetException(String message) {
        super(message);
    }

    public InvalidBetException(String message, HttpStatus cause) {
        super(message, cause);
    }

    public InvalidBetException(String field, Object value, String constraint) {
        super(String.format("Invalid bet: field '%s' value '%s' violates constraint '%s'",
                field, value, constraint));
    }
}