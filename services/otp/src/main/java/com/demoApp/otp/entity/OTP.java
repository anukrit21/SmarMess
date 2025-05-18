package com.demoApp.otp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "otps")
public class OTP {
    // Explicit no-arg constructor
    public OTP() {}
    // Explicit all-args constructor
    public OTP(Long id, String email, String phoneNumber, String otpCode, java.time.LocalDateTime expiresAt, boolean isVerified, java.time.LocalDateTime createdAt, int retryCount, boolean isActive, java.time.LocalDateTime updatedAt, RecipientType recipientType, OtpPurpose purpose, Long userId, String token) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.otpCode = otpCode;
        this.expiresAt = expiresAt;
        this.isVerified = isVerified;
        this.createdAt = createdAt;
        this.retryCount = retryCount;
        this.isActive = isActive;
        this.updatedAt = updatedAt;
        this.recipientType = recipientType;
        this.purpose = purpose;
        this.userId = userId;
        this.token = token;
    }
    // --- manually added getters and setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getOtpCode() { return otpCode; }
    public void setOtpCode(String otpCode) { this.otpCode = otpCode; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean isVerified) { this.isVerified = isVerified; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public int getRetryCount() { return retryCount; }
    public void setRetryCount(int retryCount) { this.retryCount = retryCount; }
    public boolean isActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public RecipientType getRecipientType() { return recipientType; }
    public void setRecipientType(RecipientType recipientType) { this.recipientType = recipientType; }
    public OtpPurpose getPurpose() { return purpose; }
    public void setPurpose(OtpPurpose purpose) { this.purpose = purpose; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    // --- end manual getters/setters ---

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "otp_code", nullable = false)
    private String otpCode;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "retry_count")
    private int retryCount;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "recipient_type")
    private RecipientType recipientType;

    @Enumerated(EnumType.STRING)
    @Column(name = "purpose")
    private OtpPurpose purpose;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "token")
    private String token;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        isActive = true;
        retryCount = 0;
    }

    public enum RecipientType {
        EMAIL,
        PHONE
    }

    public enum OtpPurpose {
        LOGIN,
        REGISTRATION,
        PASSWORD_RESET,
        VERIFICATION
    }
} 