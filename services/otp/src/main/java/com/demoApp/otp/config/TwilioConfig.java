package com.demoApp.otp.config;

import lombok.extern.slf4j.Slf4j;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Twilio Configuration
 * Generate own account sid, authToken, trialNumber
 * from https://console.twilio.com/
 */
@Configuration
public class TwilioConfig {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TwilioConfig.class);

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.verify.service.sid}")
    private String verifyServiceSid;

    public void init() {
        try {
            Twilio.init(accountSid, authToken);
            log.info("Twilio initialized successfully");
        } catch (Exception e) {
            log.error("Failed to initialize Twilio: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize Twilio", e);
        }
    }

    public void sendOtp(String phoneNumber) {
        try {
            Verification.creator(verifyServiceSid, phoneNumber, "sms")
                    .create();
            log.info("OTP sent successfully to {}", phoneNumber);
        } catch (Exception e) {
            log.error("Error sending OTP to {}: {}", phoneNumber, e.getMessage());
            throw new RuntimeException("Failed to send OTP", e);
        }
    }

    public boolean verifyOtp(String phoneNumber, String otp) {
        try {
            VerificationCheck verificationCheck = VerificationCheck.creator(verifyServiceSid)
                    .setTo(phoneNumber)
                    .setCode(otp)
                    .create();
            return "approved".equals(verificationCheck.getStatus());
        } catch (Exception e) {
            log.error("Error verifying OTP for {}: {}", phoneNumber, e.getMessage());
            return false;
        }
    }
}
