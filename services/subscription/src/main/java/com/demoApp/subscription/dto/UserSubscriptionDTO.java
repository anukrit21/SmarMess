package com.demoApp.subscription.dto;

import com.demoApp.subscription.enums.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSubscriptionDTO {
    private Long userId;
    private Long subscriptionId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private SubscriptionStatus status;
    private Boolean paymentCompleted;
    private String paymentTransactionId;
    private String mealDeliveryAddress;
    private String mealDeliveryInstructions;
    
    // Explicit getters and setters
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getSubscriptionId() {
        return subscriptionId;
    }
    
    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public LocalDateTime getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    
    public SubscriptionStatus getStatus() {
        return status;
    }
    
    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }
    
    public Boolean getPaymentCompleted() {
        return paymentCompleted;
    }
    
    public void setPaymentCompleted(Boolean paymentCompleted) {
        this.paymentCompleted = paymentCompleted;
    }
    
    public String getPaymentTransactionId() {
        return paymentTransactionId;
    }
    
    public void setPaymentTransactionId(String paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }
    
    public String getMealDeliveryAddress() {
        return mealDeliveryAddress;
    }
    
    public void setMealDeliveryAddress(String mealDeliveryAddress) {
        this.mealDeliveryAddress = mealDeliveryAddress;
    }
    
    public String getMealDeliveryInstructions() {
        return mealDeliveryInstructions;
    }
    
    public void setMealDeliveryInstructions(String mealDeliveryInstructions) {
        this.mealDeliveryInstructions = mealDeliveryInstructions;
    }
} 