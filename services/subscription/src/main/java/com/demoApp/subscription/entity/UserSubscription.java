package com.demoApp.subscription.entity;

import com.demoApp.subscription.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_subscriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSubscription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;
    
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;
    
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    
    @Column(name = "is_payment_completed", nullable = false)
    private boolean isPaymentCompleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SubscriptionStatus status;
    
    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Helper method to check if the subscription is active
    @Transient
    public boolean isActive() {
        return isActive;
    }
    
    // Helper method to get days remaining in the subscription
    @Transient
    public long getDaysRemaining() {
        if (LocalDateTime.now().isAfter(endDate)) return 0;
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDateTime.now(), endDate);
    }
    
    // Getters and setters for all fields
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Subscription getSubscription() {
        return subscription;
    }
    
    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
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
    
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    public boolean isPaymentCompleted() {
        return isPaymentCompleted;
    }
    
    public void setPaymentCompleted(boolean isPaymentCompleted) {
        this.isPaymentCompleted = isPaymentCompleted;
    }
    
    public SubscriptionStatus getStatus() {
        return status;
    }
    
    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }
    
    public void setCancelledAt(LocalDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Builder pattern for UserSubscription
    public static UserSubscriptionBuilder builder() {
        return new UserSubscriptionBuilder();
    }
    
    public static class UserSubscriptionBuilder {
        private Long id;
        private User user;
        private Subscription subscription;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private boolean isActive;
        private boolean isPaymentCompleted;
        private SubscriptionStatus status;
        private LocalDateTime cancelledAt;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        
        public UserSubscriptionBuilder id(Long id) {
            this.id = id;
            return this;
        }
        
        public UserSubscriptionBuilder user(User user) {
            this.user = user;
            return this;
        }
        
        public UserSubscriptionBuilder subscription(Subscription subscription) {
            this.subscription = subscription;
            return this;
        }
        
        public UserSubscriptionBuilder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }
        
        public UserSubscriptionBuilder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }
        
        public UserSubscriptionBuilder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }
        
        public UserSubscriptionBuilder isPaymentCompleted(boolean isPaymentCompleted) {
            this.isPaymentCompleted = isPaymentCompleted;
            return this;
        }
        
        public UserSubscriptionBuilder status(SubscriptionStatus status) {
            this.status = status;
            return this;
        }
        
        public UserSubscriptionBuilder cancelledAt(LocalDateTime cancelledAt) {
            this.cancelledAt = cancelledAt;
            return this;
        }
        
        public UserSubscriptionBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        
        public UserSubscriptionBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }
        
        public UserSubscription build() {
            UserSubscription userSubscription = new UserSubscription();
            userSubscription.id = this.id;
            userSubscription.user = this.user;
            userSubscription.subscription = this.subscription;
            userSubscription.startDate = this.startDate;
            userSubscription.endDate = this.endDate;
            userSubscription.isActive = this.isActive;
            userSubscription.isPaymentCompleted = this.isPaymentCompleted;
            userSubscription.status = this.status;
            userSubscription.cancelledAt = this.cancelledAt;
            userSubscription.createdAt = this.createdAt;
            userSubscription.updatedAt = this.updatedAt;
            return userSubscription;
        }
    }
} 