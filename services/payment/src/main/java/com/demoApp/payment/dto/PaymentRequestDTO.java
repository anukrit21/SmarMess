package com.demoApp.payment.dto;

import com.demoApp.payment.entity.PaymentMethodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

/**
 * DTO for payment request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {
    // --- Explicit Getters and Setters for all fields below ---
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }
    private String currency;

    public String getCurrency() {
        return currency;
    }
    private PaymentMethodType paymentMethod;
    private Long orderId;
    private String description;

    public String getDescription() {
        return description;
    }
    
    // Card details
    private String cardNumber;
    private Integer expMonth;
    private Integer expYear;
    private String cvc;

    public String getCardNumber() { return cardNumber; }
    public Integer getExpMonth() { return expMonth; }
    public Integer getExpYear() { return expYear; }
    public String getCvc() { return cvc; }
    
    // UPI details
    private String upiId;
    
    // Bank details
    private String bankCode;
    
    // Wallet details
    private String walletId;
    
    // Additional metadata
    private String customerId;

    private Map<String, String> metadata;

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public PaymentMethodType getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodType paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // If needed for logic using the enum:
    public PaymentMethodType getPaymentMethodType() {
        return paymentMethod;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
