package com.demoApp.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessAnalyticsDTO {
    private Long totalOrders;
    private Double totalRevenue;
    private int totalMealsServed;
    private Long activeUsers;
    private Long totalSubscriptions;
    private Double averageOrderValue;
    private Long totalDeliveries;
    private Double deliverySuccessRate;
} 