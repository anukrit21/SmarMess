package com.demoApp.order.dto;

import com.demoApp.order.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private Long userId;
    private Long messId;
    private List<Long> menuItemIds;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime orderTime;
    private LocalDateTime deliveryTime;
    private String deliveryPersonId;
    private String paymentId;
    private String paymentStatus;
} 