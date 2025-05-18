package com.demoApp.otp.controller;

import com.demoApp.otp.dto.OtpRequestDTO;
import com.demoApp.otp.dto.OtpResponseDTO;
import com.demoApp.otp.dto.OtpVerificationDTO;
import com.demoApp.otp.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OtpController {
    private final OtpService otpService;

    @PostMapping("/generate")
    public ResponseEntity<OtpResponseDTO> generateOTP(@RequestBody OtpRequestDTO request) {
        OtpResponseDTO response = otpService.generateOtp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<OtpResponseDTO> verifyOTP(@RequestBody OtpVerificationDTO request) {
        OtpResponseDTO response = otpService.verifyOtp(request);
        return ResponseEntity.ok(response);
    }
}
