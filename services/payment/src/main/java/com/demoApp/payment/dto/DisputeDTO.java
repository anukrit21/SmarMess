package com.demoApp.payment.dto;

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
public class DisputeDTO {
    private Long paymentId;
    private Long orderId;
    private Long userId;
    private BigDecimal amount;
    private String disputeId;
    private String status;
    private String reason;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;
} 