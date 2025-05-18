package com.demoApp.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for representing delivery information
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDTO {

    private Long id;

    private Long orderId;

    private String trackingNumber;

    private String status;

    private String carrier;

    private String serviceType;

    private LocalDateTime estimatedDeliveryDate;

    private LocalDateTime actualDeliveryDate;

    private String recipientName;

    private String recipientPhone;

    private String recipientEmail;

    private String deliveryAddress;

    private String city;

    private String state;

    private String country;

    private String postalCode;

    private String notes;

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;
} 