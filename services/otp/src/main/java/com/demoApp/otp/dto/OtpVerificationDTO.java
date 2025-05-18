package com.demoApp.otp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class OtpVerificationDTO {
    private String phoneNumber;
    private String otp;

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }

    public OtpVerificationDTO() {}

    public OtpVerificationDTO(String phoneNumber, String otp) {
        this.phoneNumber = phoneNumber;
        this.otp = otp;
    }
}