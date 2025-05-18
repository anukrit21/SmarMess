package com.demoApp.otp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Entity for storing OTP (One-Time Password) information
 */
@Entity
@Table(name = "otps")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "otp")
    private String otp;

    @Column(name = "otp_type")
    private String otpType;

    @Column(name = "purpose", nullable = false)
    @Enumerated(EnumType.STRING)
    private OtpPurpose purpose;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "retry_count")
    private int retryCount;

    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "recipient_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RecipientType recipientType;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @Column(name = "user_id")
    private Long userId;
    
    private String token;
    
    @Builder.Default
    private int maxRetries = 3;
    
    @Builder.Default
    @Column(name = "attempts", nullable = false)
    private int attempts = 0;

    /**
     * Enum for different OTP purposes
     */
    public enum OtpPurpose {
        REGISTRATION,
        PASSWORD_RESET,
        LOGIN,
        EMAIL_VERIFICATION,
        PHONE_VERIFICATION,
        TRANSACTION_AUTHORIZATION
    }

    /**
     * Enum for recipient types
     */
    public enum RecipientType {
        EMAIL,
        PHONE
    }

    /**
     * Check if the OTP is expired
     * @return true if expired, false otherwise
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryTime);
    }

    /**
     * Check if the OTP is valid (not expired and not used)
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        return !isExpired() && !isVerified;
    }

    /**
     * Mark the OTP as used
     */
    public void markAsUsed() {
        this.isVerified = true;
        this.verifiedAt = LocalDateTime.now();
    }

    /**
     * Increment retry count
     */
    public void incrementRetryCount() {
        this.retryCount++;
    }
    
    /**
     * Increment attempts count when OTP is checked
     */
    public void incrementAttempts() {
        this.attempts++;
    }
    
    /**
     * Check if maximum retries exceeded
     * @return true if maximum retries exceeded, false otherwise
     */
    public boolean isMaxRetriesExceeded() {
        return this.retryCount >= this.maxRetries;
    }
    
    /**
     * Check if maximum attempts exceeded
     * @return true if maximum attempts exceeded, false otherwise
     */
    public boolean isMaxAttemptsExceeded() {
        return this.attempts >= this.maxRetries;
    }
    
    /**
     * Get remaining time until expiry in seconds
     * @return seconds until expiry, 0 if already expired
     */
    public long getRemainingTimeInSeconds() {
        if (isExpired()) {
            return 0;
        }
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), expiryTime);
    }
    
    /**
     * Verify OTP against provided value
     * @param otpValue OTP value to check
     * @return true if matched, false otherwise
     */
    public boolean verifyOtp(String otpValue) {
        incrementAttempts();
        if (!isValid()) {
            return false;
        }
        
        boolean isMatch = this.otp.equals(otpValue);
        if (isMatch) {
            markAsUsed();
        } else if (isMaxAttemptsExceeded()) {
            // Auto expire OTP after max attempts
            this.expiryTime = LocalDateTime.now();
        }
        return isMatch;
    }
    
    /**
     * Reset retry count
     */
    public void resetRetryCount() {
        this.retryCount = 0;
    }
    
    @PrePersist
    protected void onCreate() {
        if (expiryTime == null) {
            // Default expiry of 5 minutes if not set
            expiryTime = LocalDateTime.now().plusMinutes(5);
        }
    }
} 