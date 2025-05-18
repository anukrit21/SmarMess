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
public class UserSubscriptionResponseDTO {
    private Long id;
    private Long userId;
    private Long subscriptionId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private SubscriptionStatus status;
    private boolean isActive;
    private boolean isPaymentCompleted;
    
    // Explicit getters and setters
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
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    public boolean isPaymentCompleted() {
        return isPaymentCompleted;
    }
    
    public void setPaymentCompleted(boolean isPaymentCompleted) {
        this.isPaymentCompleted = isPaymentCompleted;
    }
    
    // Explicit builder implementation
    public static UserSubscriptionResponseDTOBuilder builder() {
        return new UserSubscriptionResponseDTOBuilder();
    }
    
    public static class UserSubscriptionResponseDTOBuilder {
        private Long id;
        private Long userId;
        private Long subscriptionId;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private SubscriptionStatus status;
        private boolean isActive;
        private boolean isPaymentCompleted;
        
        public UserSubscriptionResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }
        
        public UserSubscriptionResponseDTOBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }
        
        public UserSubscriptionResponseDTOBuilder subscriptionId(Long subscriptionId) {
            this.subscriptionId = subscriptionId;
            return this;
        }
        
        public UserSubscriptionResponseDTOBuilder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }
        
        public UserSubscriptionResponseDTOBuilder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }
        
        public UserSubscriptionResponseDTOBuilder status(SubscriptionStatus status) {
            this.status = status;
            return this;
        }
        
        public UserSubscriptionResponseDTOBuilder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }
        
        public UserSubscriptionResponseDTOBuilder isPaymentCompleted(boolean isPaymentCompleted) {
            this.isPaymentCompleted = isPaymentCompleted;
            return this;
        }
        
        public UserSubscriptionResponseDTO build() {
            UserSubscriptionResponseDTO dto = new UserSubscriptionResponseDTO();
            dto.id = this.id;
            dto.userId = this.userId;
            dto.subscriptionId = this.subscriptionId;
            dto.startDate = this.startDate;
            dto.endDate = this.endDate;
            dto.status = this.status;
            dto.isActive = this.isActive;
            dto.isPaymentCompleted = this.isPaymentCompleted;
            return dto;
        }
    }
}