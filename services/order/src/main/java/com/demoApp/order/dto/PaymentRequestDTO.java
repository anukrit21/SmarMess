package com.demoApp.order.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {
    private String orderId;
    private Long userId;
    private BigDecimal amount;
    private String paymentMethod;
    private String currency;
    // Add more fields as needed
} 