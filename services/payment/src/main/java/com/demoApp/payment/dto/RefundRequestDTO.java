package com.demoApp.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

/**
 * DTO for refund request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundRequestDTO {

    private String paymentIntentId;
    private BigDecimal amount;
    private String reason;
    private String description;
    private String customerId;
    private String orderId;

    public Long getPaymentId() { return Long.parseLong(paymentIntentId); }

    public void setPaymentId(Long paymentId) { this.paymentIntentId = paymentId != null ? paymentId.toString() : null; }
} 