package com.demoApp.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long id;
    private String paymentId;
    private Long orderId;
    private Long userId;
    private Long merchantId;
    private Long ownerId;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private String status;
    private String transactionId;
    private String disputeId;
    private boolean success;
      private String errorMessage;
    private String paymentProviderReference;
    private String orderReference;
    private Long productId;
    private Long subscriptionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
    private LocalDateTime refundedAt;
    private LocalDateTime disputedAt;
} 