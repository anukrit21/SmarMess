package com.demoApp.subscription.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception class used when a requested resource is not found.
 * Typically thrown in service or controller layers when an entity lookup fails.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }

    // Constructor with custom message
    public ResourceNotFoundException(String message) {
        super(message);
    }

    // Constructor with custom message and cause
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause only
    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
