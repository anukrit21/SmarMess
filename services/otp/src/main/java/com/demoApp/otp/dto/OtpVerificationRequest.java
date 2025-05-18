package com.demoApp.otp.dto;

import lombok.Data;

@Data
public class OtpVerificationRequest {
    private String phoneNumber;
    private String otp;
} 