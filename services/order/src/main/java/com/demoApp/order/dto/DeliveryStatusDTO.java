package com.demoApp.order.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStatusDTO {
    private String status;
    private String location;
    private String message;
} 