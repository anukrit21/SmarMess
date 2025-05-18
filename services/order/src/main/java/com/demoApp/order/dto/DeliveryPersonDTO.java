package com.demoApp.order.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPersonDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private String currentLocation;
    private Double rating;
} 