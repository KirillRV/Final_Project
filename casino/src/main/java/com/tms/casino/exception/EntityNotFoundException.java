package com.tms.casino.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
        super("Requested entity not found");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entityName, Object identifier) {
        super(String.format("%s not found with identifier: %s", entityName, identifier.toString()));
    }

    public EntityNotFoundException(Class<?> entityClass, Object identifier) {
        this(entityClass.getSimpleName(), identifier);
    }
}