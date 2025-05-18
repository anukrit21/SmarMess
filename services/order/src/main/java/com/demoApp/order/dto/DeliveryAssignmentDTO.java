package com.demoApp.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAssignmentDTO {
    private Long orderId;
    private Long deliveryPersonId;
    private String deliveryLocation;
    private String specialInstructions;
} 