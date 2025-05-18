package com.demoApp.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionAnalyticsDTO {
    private Long totalSubscriptions; // Long to hold the total count of subscriptions
    private Long activeSubscriptions; // Long for active subscriptions
    private BigDecimal revenue; // Revenue from subscriptions
    private Map<String, Long> planDistribution; // Distribution of subscriptions by plan
    private Map<String, Long> statusDistribution; // Distribution by subscription status
    private Double churnRate; // Churn rate (as a percentage or ratio)
}
