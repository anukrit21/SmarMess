package com.demoApp.otp.service;

import com.demoApp.otp.config.TwilioConfig;
import com.demoApp.otp.dto.OtpRequestDTO;
import com.demoApp.otp.dto.OtpVerificationDTO;
import com.demoApp.otp.dto.OtpResponseDTO;
import com.demoApp.otp.entity.OTP;
import com.demoApp.otp.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OtpService.class);

    private final OtpRepository otpRepository;
    private final TwilioConfig twilioConfig;
    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRY_MINUTES = 5;
    private static final int MAX_ATTEMPTS = 3;

    @Transactional
    public OtpResponseDTO generateOtp(OtpRequestDTO request) {
        String otpCode = generateRandomOtp();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES);

        OTP otp = new OTP();
        otp.setPhoneNumber(request.getPhoneNumber());
        otp.setEmail(request.getEmail());
        otp.setOtpCode(otpCode);
        otp.setRecipientType(OTP.RecipientType.PHONE);
        otp.setPurpose(OTP.OtpPurpose.VERIFICATION);
        otp.setExpiresAt(expiryTime);
        otp.setIsActive(true);
        otp.setRetryCount(0);
        otp.setVerified(false);

        otpRepository.save(otp);

        // Send OTP via Twilio
        twilioConfig.sendOtp(request.getPhoneNumber());

        return OtpResponseDTO.builder()
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .recipientType(OTP.RecipientType.PHONE)
                .purpose(OTP.OtpPurpose.VERIFICATION)
                .expiryTime(expiryTime)
                .verified(false)
                .message("OTP sent successfully")
                .build();
    }

    public OtpResponseDTO verifyOtp(OtpVerificationDTO request) {
        OTP otp = otpRepository.findByPhoneNumberAndOtpCodeAndIsVerifiedFalse(request.getPhoneNumber(), request.getOtp())
                .orElseThrow(() -> new RuntimeException("OTP not found or already used/expired"));

        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            otp.setIsActive(false);
            otpRepository.save(otp);
            return OtpResponseDTO.builder()
                    .phoneNumber(request.getPhoneNumber())
                    .verified(false)
                    .message("OTP expired")
                    .build();
        }

        if (otp.getRetryCount() >= MAX_ATTEMPTS) {
            otp.setIsActive(false);
            otpRepository.save(otp);
            return OtpResponseDTO.builder()
                    .phoneNumber(request.getPhoneNumber())
                    .verified(false)
                    .message("Maximum attempts exceeded")
                    .build();
        }

        if (!otp.getOtpCode().equals(request.getOtp())) {
            otp.setRetryCount(otp.getRetryCount() + 1);
            otpRepository.save(otp);
            return OtpResponseDTO.builder()
                    .phoneNumber(request.getPhoneNumber())
                    .verified(false)
                    .message("Incorrect OTP")
                    .build();
        }

        otp.setVerified(true);
        otp.setIsActive(false);
        otpRepository.save(otp);

        return OtpResponseDTO.builder()
                .phoneNumber(request.getPhoneNumber())
                .verified(true)
                .message("OTP verified successfully")
                .build();
    }

    private String generateRandomOtp() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            sb.append(random.nextInt(10)); // digits 0-9
        }
        return sb.toString();
    }
}
