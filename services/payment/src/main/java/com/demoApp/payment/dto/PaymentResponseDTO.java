package com.demoApp.payment.dto;

import com.demoApp.payment.entity.PaymentMethodType;
import com.demoApp.payment.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private Long id;
    private BigDecimal amount;
    private String currency;
    private PaymentMethodType paymentMethod;
    private PaymentStatus status;
    private String paymentIntentId;
    private String customerId;
    private Long orderId;
    private String description;
    private String errorMessage;
    private String disputeId;
    private LocalDateTime disputedAt;
    private LocalDateTime refundedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Map<String, String> metadata;
    private String clientSecret;
    private String transactionId;
}
