package com.demoApp.otp.exception;

public class OtpMaxRetriesExceededException extends RuntimeException {
    public OtpMaxRetriesExceededException(String message) {
        super(message);
    }
} 