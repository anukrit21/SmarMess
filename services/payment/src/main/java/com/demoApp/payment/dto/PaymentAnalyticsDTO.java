package com.demoApp.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAnalyticsDTO {
    private BigDecimal totalAmount;
    private Long totalTransactions;
    private Map<String, Long> transactionsByStatus;
    private Map<String, BigDecimal> amountByPaymentMethod;
    private BigDecimal averageTransactionAmount;
    private Long successfulTransactions;
    private Long failedTransactions;
    private Long refundedTransactions;
    private Long disputedTransactions;
}