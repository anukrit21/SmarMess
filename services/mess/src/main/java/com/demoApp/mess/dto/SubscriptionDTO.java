package com.demoApp.mess.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.demoApp.mess.entity.Subscription;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SubscriptionDTO {
    public void setMessId(Long messId) { this.messId = messId; }

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer durationDays;
    private Subscription.SubscriptionType type;
    private boolean active;
    private Long messId;
    private String messName;
    private String imageUrl;
    private Integer mealsPerWeek;
    private String deliveryDays;
    private String mealTypes;
    private String dietaryOptions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Additional getters that might be needed
    public String getMealTypes() {
        return this.mealTypes;
    }
    
    public String getDietaryOptions() {
        return this.dietaryOptions;
    }
    
    public Integer getMealsPerWeek() {
        return this.mealsPerWeek;
    }
    
    public String getDeliveryDays() {
        return this.deliveryDays;
    }
    
    public String getImageUrl() {
        return this.imageUrl;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public BigDecimal getPrice() {
        return this.price;
    }
    
    public Integer getDurationDays() {
        return this.durationDays;
    }
    
    public Subscription.SubscriptionType getType() {
        return this.type;
    }
    
    public boolean isActive() {
        return this.active;
    }
    
    public Long getMessId() {
        return this.messId;
    }
}
