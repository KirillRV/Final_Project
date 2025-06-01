package com.tms.casino.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("Insufficient funds for this operation");
    }

    public InsufficientFundsException(String message) {
        super(message);
    }
}