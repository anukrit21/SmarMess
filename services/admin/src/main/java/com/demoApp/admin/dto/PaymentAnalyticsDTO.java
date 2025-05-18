package com.demoApp.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAnalyticsDTO {
    private Long totalTransactions;
    private Double totalAmount;
    private Long successfulTransactions;
    private Long failedTransactions;
    private Double averageTransactionValue;
    private Long pendingTransactions;
    private Double successRate;
    private String mostUsedPaymentMethod;
} 