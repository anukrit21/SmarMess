package com.demoApp.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminAnalyticsDTO {
    private Long totalUsers;
    private Long totalOrders;
    private Long totalDeliveries;
    private Long totalPayments;
    private Map<String, Long> usersByRole;
    private Map<String, Long> ordersByStatus;
    private Map<String, Long> deliveriesByStatus;
    private Map<String, Long> paymentsByStatus;
    private Map<String, Double> revenueByMonth;
    private Map<String, Long> activeUsersByDay;
    private UserAnalyticsDTO userAnalytics;
    private MessAnalyticsDTO messAnalytics;
    private OrderAnalyticsDTO orderAnalytics;
    private PaymentAnalyticsDTO paymentAnalytics;
    private DeliveryAnalyticsDTO deliveryAnalytics;
}
