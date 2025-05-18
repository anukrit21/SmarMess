package com.demoApp.payment.dto;

import com.demoApp.payment.entity.PaymentStatus;
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
public class DisputeResponseDTO {
    private Long id;
    private Long orderId;
    private Long userId;
    private String customerId;
    private BigDecimal amount;
    private String disputeId;
    private PaymentStatus status;
    private LocalDateTime disputedAt;
}
