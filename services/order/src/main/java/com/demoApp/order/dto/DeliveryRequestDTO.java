package com.demoApp.order.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRequestDTO {
    private String orderId;
    private Long messId;
    private String deliveryLocation;
    private LocalDateTime expectedDeliveryTime;
    private String specialInstructions;
} 