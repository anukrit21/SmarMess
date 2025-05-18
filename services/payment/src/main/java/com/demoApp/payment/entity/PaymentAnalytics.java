package com.demoApp.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "payment_analytics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAnalytics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_payments", nullable = false)
    @Builder.Default
    private Long totalPayments = 0L;

    @Column(name = "total_amount", nullable = false)
    @Builder.Default
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "total_refunds", nullable = false)
    @Builder.Default
    private Long totalRefunds = 0L;

    @Column(name = "total_refund_amount", nullable = false)
    @Builder.Default
    private BigDecimal totalRefundAmount = BigDecimal.ZERO;

    @Column(name = "total_disputes", nullable = false)
    @Builder.Default
    private Long totalDisputes = 0L;

    @ElementCollection
    @CollectionTable(name = "payment_method_counts", 
        joinColumns = @JoinColumn(name = "analytics_id"))
    @MapKeyColumn(name = "payment_method")
    @Column(name = "count")
    @Builder.Default
    private Map<String, Long> paymentMethodCounts = new HashMap<>();
} 