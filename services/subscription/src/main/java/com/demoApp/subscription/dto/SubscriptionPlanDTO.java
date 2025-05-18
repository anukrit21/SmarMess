package com.demoApp.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlanDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String currency;
    private Integer durationInMonths;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 