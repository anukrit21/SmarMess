package com.demoApp.mess.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_subscriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSubscription {
    
    public enum SubscriptionStatus {
        ACTIVE, EXPIRED, CANCELLED
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(nullable = false)
    private Long userId;
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;
    
    public Subscription getSubscription() {
        return subscription;
    }
    
    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
    
    @Column(nullable = false)
    private LocalDate startDate;
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    @Column(nullable = false)
    private LocalDate endDate;
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;
    
    public SubscriptionStatus getStatus() {
        return status;
    }
    
    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    private LocalDateTime updatedAt;
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Builder.Default
    @Column(nullable = false)
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
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Helper method to check if the subscription is active
    @Transient
    public boolean isActive() {
        return status == SubscriptionStatus.ACTIVE && 
               LocalDate.now().isBefore(endDate.plusDays(1));
    }
    
    // Helper method to get days remaining in the subscription
    @Transient
    public long getDaysRemaining() {
        if (LocalDate.now().isAfter(endDate)) return 0;
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), endDate);
    }
} 