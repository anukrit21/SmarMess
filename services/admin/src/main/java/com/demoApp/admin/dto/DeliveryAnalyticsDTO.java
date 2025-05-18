package com.demoApp.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAnalyticsDTO {
    private Long totalDeliveries;
    private Long completedDeliveries;
    private Long pendingDeliveries;
    private Long failedDeliveries;
    private Double averageDeliveryTime;
    private Double successRate;
    private Long activeDeliveryPersons;
    private Double averageRating;
} 