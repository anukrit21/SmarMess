package com.demoApp.mess.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.demoApp.mess.entity.UserSubscription;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSubscriptionDTO {
    
    private Long id;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    @NotNull(message = "Subscription ID is required")
    private Long subscriptionId;
    
    public Long getSubscriptionId() {
        return subscriptionId;
    }
    
    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
    
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    @Builder.Default
    private UserSubscription.SubscriptionStatus status = UserSubscription.SubscriptionStatus.ACTIVE;
    
    public UserSubscription.SubscriptionStatus getStatus() {
        return status;
    }
    
    public void setStatus(UserSubscription.SubscriptionStatus status) {
        this.status = status;
    }
    
    @Builder.Default
    private boolean paymentCompleted = false;
    
    public boolean isPaymentCompleted() {
        return paymentCompleted;
    }
    
    public void setPaymentCompleted(boolean paymentCompleted) {
        this.paymentCompleted = paymentCompleted;
    }
    
    private String paymentTransactionId;
    
    public String getPaymentTransactionId() {
        return paymentTransactionId;
    }
    
    public void setPaymentTransactionId(String paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }
    
    private String mealDeliveryAddress;
    
    public String getMealDeliveryAddress() {
        return mealDeliveryAddress;
    }
    
    public void setMealDeliveryAddress(String mealDeliveryAddress) {
        this.mealDeliveryAddress = mealDeliveryAddress;
    }
    
    private String mealDeliveryInstructions;
    
    public String getMealDeliveryInstructions() {
        return mealDeliveryInstructions;
    }
    
    public void setMealDeliveryInstructions(String mealDeliveryInstructions) {
        this.mealDeliveryInstructions = mealDeliveryInstructions;
    }
    
    // DTO for responses that includes the subscription details
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserSubscriptionResponseDTO {
        private Long id;
        private Long userId;
        private SubscriptionDTO subscription;
        private LocalDate startDate;
        private LocalDate endDate;
        private UserSubscription.SubscriptionStatus status;
        private boolean paymentCompleted;
        private String paymentTransactionId;
        private String mealDeliveryAddress;
        private String mealDeliveryInstructions;
        private long daysRemaining;
        private boolean active;
        
        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public Long getUserId() {
            return userId;
        }
        
        public void setUserId(Long userId) {
            this.userId = userId;
        }
        
        public SubscriptionDTO getSubscription() {
            return subscription;
        }
        
        public void setSubscription(SubscriptionDTO subscription) {
            this.subscription = subscription;
        }
        
        public LocalDate getStartDate() {
            return startDate;
        }
        
        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }
        
        public LocalDate getEndDate() {
            return endDate;
        }
        
        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }
        
        public UserSubscription.SubscriptionStatus getStatus() {
            return status;
        }
        
        public void setStatus(UserSubscription.SubscriptionStatus status) {
            this.status = status;
        }
        
        public boolean isPaymentCompleted() {
            return paymentCompleted;
        }
        
        public void setPaymentCompleted(boolean paymentCompleted) {
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
        
        public long getDaysRemaining() {
            return daysRemaining;
        }
        
        public void setDaysRemaining(long daysRemaining) {
            this.daysRemaining = daysRemaining;
        }
        
        public boolean isActive() {
            return active;
        }
        
        public void setActive(boolean active) {
            this.active = active;
        }
    }
} 