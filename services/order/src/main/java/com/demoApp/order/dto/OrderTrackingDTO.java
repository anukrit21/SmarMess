package com.demoApp.order.dto;

import com.demoApp.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderTrackingDTO {
    private Long id;
    private OrderStatus status;
    private String deliveryLocation;
    private LocalDateTime estimatedDeliveryTime;
    private String deliveryPerson;
    private String deliveryPersonContact;
    private List<OrderStatus> statusHistory;
} 