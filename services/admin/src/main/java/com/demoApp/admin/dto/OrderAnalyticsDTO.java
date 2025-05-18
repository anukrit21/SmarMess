package com.demoApp.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAnalyticsDTO {
    private Long totalOrders;
    private Double totalRevenue;
    private Long pendingOrders;
    private Long completedOrders;
    private Long cancelledOrders;
    private Double averageOrderValue;
    private Long peakOrderHour;
    private Double orderCompletionRate;
} 