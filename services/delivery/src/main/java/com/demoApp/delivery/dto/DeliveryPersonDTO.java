package com.demoApp.delivery.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class DeliveryPersonDTO {
    private Long id;
    private String name;
    private String mobileNumber;
    private String email;
    private String password;
    private String vehicleNumber;
    private String vehicleType;
    private boolean isAvailable;
    private double averageRating;
    private int totalRatings;
    private String status;
    private String zone;
} 