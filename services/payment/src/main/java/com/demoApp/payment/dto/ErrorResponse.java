package com.demoApp.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String timestamp;
    private String path;
    private String errorCode;

    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }
} 