package com.demoApp.otp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import com.demoApp.otp.service.UserDetailsServiceImpl;

import com.demoApp.otp.dto.AuthenticationRequest;
import com.demoApp.otp.dto.AuthenticationResponse;
import com.demoApp.otp.dto.OtpVerificationDTO;
import com.demoApp.otp.dto.OtpRequestDTO; // make sure this is also correctly imported
import com.demoApp.otp.service.OtpService;
import com.demoApp.otp.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final OtpService otpService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        log.debug("Processing login request for user: {}", request.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        // Load UserDetails by username
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(AuthenticationResponse.builder().token(token).build());
    }

    @PostMapping("/send-otp")
    public ResponseEntity<OtpRequestDTO> sendOtp(@RequestBody OtpRequestDTO request) {
        log.debug("Sending OTP to phone number: {}", request.getPhoneNumber());
        otpService.generateOtp(request);
        return ResponseEntity.ok(request);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<AuthenticationResponse> verifyOtp(@RequestBody OtpVerificationDTO request) {
        log.debug("Verifying OTP for phone number: {}", request.getPhoneNumber());
        otpService.verifyOtp(request);
        // Load UserDetails by phone number
        UserDetails userDetails = userDetailsService.loadUserByPhoneNumber(request.getPhoneNumber());
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(AuthenticationResponse.builder().token(token).build());
    }
}
