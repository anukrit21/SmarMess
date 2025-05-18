package com.demoApp.otp.repository;

import com.demoApp.otp.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for OTP entity
 */
@Repository
public interface OtpRepository extends JpaRepository<OTP, Long> {
    // Removed duplicate method


    // Find the latest unverified OTP for phone number by type and purpose
    @Query("SELECT o FROM OTP o WHERE o.phoneNumber = ?1 AND o.recipientType = ?2 AND o.purpose = ?3 AND o.isVerified = false ORDER BY o.createdAt DESC")
    Optional<OTP> findLatestValidOtp(String phoneNumber, OTP.RecipientType recipientType, OTP.OtpPurpose purpose);

    // Find OTP by phone number, recipient type, code, and purpose
    Optional<OTP> findByPhoneNumberAndRecipientTypeAndOtpCodeAndPurpose(
            String phoneNumber,
            OTP.RecipientType recipientType,
            String otpCode,
            OTP.OtpPurpose purpose
    );

    // Find all active (unverified + not expired) OTPs for phone number
    List<OTP> findByPhoneNumberAndRecipientTypeAndIsVerifiedFalseAndExpiresAtAfter(
            String phoneNumber,
            OTP.RecipientType recipientType,
            LocalDateTime now
    );

    // Find all unverified OTPs for a phone number


    // Most recent unverified OTP for a phone number
    Optional<OTP> findTopByPhoneNumberAndIsVerifiedFalseOrderByCreatedAtDesc(String phoneNumber);

    // Most recent OTP for a phone number


    // Find OTP by user ID and purpose
    Optional<OTP> findByUserIdAndPurposeAndIsVerifiedFalse(Long userId, OTP.OtpPurpose purpose);

    // Find OTP by token
    Optional<OTP> findByToken(String token);

    // Count OTPs generated recently for phone number
    @Query("SELECT COUNT(o) FROM OTP o WHERE o.phoneNumber = ?1 AND o.recipientType = ?2 AND o.createdAt > ?3 AND o.purpose = ?4")
    int countRecentOtps(String phoneNumber, OTP.RecipientType recipientType, LocalDateTime since, OTP.OtpPurpose purpose);

    // Find expired but unverified OTPs (based on expiresAt)
    List<OTP> findByIsVerifiedFalseAndExpiresAtBefore(LocalDateTime now);

    // Find OTPs by purpose
    List<OTP> findByPurpose(OTP.OtpPurpose purpose);

    // Delete expired OTPs (based on expiresAt)
    @Modifying
    @Transactional
    @Query("DELETE FROM OTP o WHERE o.expiresAt < ?1")
    void deleteExpiredOtps(LocalDateTime cutoffDate);

    // Count failed attempts by phone number (field 'attempts' does not exist, so this method should be removed or implemented correctly if needed)
    // REMOVED: Integer countFailedAttemptsByRecipient(String recipient, LocalDateTime since);

    // Count failed attempts by phone number (field 'attempts' does not exist, so this method should be removed or implemented correctly if needed)
    // REMOVED: Integer countFailedAttemptsByPhoneNumber(String phoneNumber, LocalDateTime since);

    // Find OTP by phone number
    Optional<OTP> findByPhoneNumberAndIsVerifiedFalse(String phoneNumber);

    // Find all unverified OTPs by phone number
    List<OTP> findByPhoneNumberAndIsVerifiedFalseOrderByCreatedAtDesc(String phoneNumber);

    // Most recent OTP for a phone number


    Optional<OTP> findByPhoneNumberAndOtpCode(String phoneNumber, String otpCode);
    Optional<OTP> findByPhoneNumber(String phoneNumber);

    Optional<OTP> findByEmailAndOtpCodeAndIsVerifiedFalse(String email, String otpCode);
    Optional<OTP> findByPhoneNumberAndOtpCodeAndIsVerifiedFalse(String phoneNumber, String otpCode);

    // Methods using phoneNo, otpType do not match entity fields and are removed.
}
