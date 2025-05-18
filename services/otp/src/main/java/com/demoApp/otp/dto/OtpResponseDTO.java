package com.demoApp.otp.dto;

import com.demoApp.otp.entity.OTP;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpResponseDTO {
    // --- existing fields ---
    private String phoneNumber;
    private String email;
    private OTP.RecipientType recipientType;
    private OTP.OtpPurpose purpose;
    private LocalDateTime expiryTime;
    private boolean verified;
    private String message;

    // --- manual builder ---
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private String phoneNumber;
        private String email;
        private OTP.RecipientType recipientType;
        private OTP.OtpPurpose purpose;
        private LocalDateTime expiryTime;
        private boolean verified;
        private String message;
        public Builder phoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder recipientType(OTP.RecipientType recipientType) { this.recipientType = recipientType; return this; }
        public Builder purpose(OTP.OtpPurpose purpose) { this.purpose = purpose; return this; }
        public Builder expiryTime(LocalDateTime expiryTime) { this.expiryTime = expiryTime; return this; }
        public Builder verified(boolean verified) { this.verified = verified; return this; }
        public Builder message(String message) { this.message = message; return this; }
        public OtpResponseDTO build() {
            OtpResponseDTO dto = new OtpResponseDTO();
            dto.phoneNumber = this.phoneNumber;
            dto.email = this.email;
            dto.recipientType = this.recipientType;
            dto.purpose = this.purpose;
            dto.expiryTime = this.expiryTime;
            dto.verified = this.verified;
            dto.message = this.message;
            return dto;
        }
    }

}
