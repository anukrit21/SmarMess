package com.demoApp.delivery.dto;

import com.demoApp.delivery.entity.Delivery;
import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@Builder
public class DeliveryStatusHistory {
    private Delivery.DeliveryStatus status;
    private LocalDateTime timestamp;
    private String notes;
} 