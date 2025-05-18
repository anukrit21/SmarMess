package com.demoApp.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlanUpdateDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private String currency;
    private Integer durationInMonths;

    public String getName() { return name; }
    public String getDescription() { return description; }
    public java.math.BigDecimal getPrice() { return price; }
    public String getCurrency() { return currency; }
    public Integer getDurationInMonths() { return durationInMonths; }
} 