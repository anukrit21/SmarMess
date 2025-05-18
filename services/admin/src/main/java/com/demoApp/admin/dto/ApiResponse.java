package com.demoApp.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private String timestamp;
    private Map<String, String> errors;

    // Constructor for success with message only
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }

    // Constructor for success with message and errors
    public ApiResponse(boolean success, String message, Map<String, String> errors) {
        this.success = success;
        this.message = message;
        this.errors = errors;
        this.timestamp = LocalDateTime.now().toString();
    }

    // Constructor for success with message and data
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now().toString();
    }

    // Full constructor with all fields
    public ApiResponse(boolean success, String message, T data, Map<String, String> errors) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errors = errors;
        this.timestamp = LocalDateTime.now().toString();
    }

    // ✅ Static factory method for success with data
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Operation successful", data);
    }

    // ✅ Static factory method for success without data
    public static ApiResponse<Void> success() {
        return new ApiResponse<>(true, "Operation successful", null);
    }

    // ✅ Static factory method for success with custom message and data
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    // ✅ Static factory method for success with custom message only
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, message, (T) null);
    }

    // ✅ Static factory method for error with message only
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, (T) null);
    }

    // ✅ Static factory method for error with message and data and error map
    public static <T> ApiResponse<T> error(String message, T data, Map<String, String> errors) {
        return new ApiResponse<>(false, message, data, errors);
    }

    // ✅ Static factory method for error with just message and error map
    public static <T> ApiResponse<T> error(String message, Map<String, String> errors) {
        return new ApiResponse<>(false, message, null, errors);
    }

    // ✅ Optional: fallback factory method
    public static <T> ApiResponse<T> of(boolean success, String message, T data, Map<String, String> errors) {
        return new ApiResponse<>(success, message, data, errors);
    }
}
