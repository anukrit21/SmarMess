package com.demoApp.subscription.entity;

import com.demoApp.subscription.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "subscriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id", nullable = false)
    private SubscriptionPlan plan;

    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "duration_months")
    private Integer durationMonths;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @ElementCollection
    @CollectionTable(name = "subscription_features", joinColumns = @JoinColumn(name = "subscription_id"))
    @Column(name = "feature")
    private List<String> features;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = SubscriptionStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Integer getDurationMonths() {
        return durationMonths;
    }
    
    public void setDurationMonths(Integer durationMonths) {
        this.durationMonths = durationMonths;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<String> getFeatures() {
        return features;
    }
    
    public void setFeatures(List<String> features) {
        this.features = features;
    }
    
    public Long getId() {
        return id;
    }
    
    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setPlan(SubscriptionPlan plan) { this.plan = plan; }
    public void setStatus(com.demoApp.subscription.enums.SubscriptionStatus status) { this.status = status; }
    public void setStartDate(java.time.LocalDateTime startDate) { this.startDate = startDate; }
    public void setEndDate(java.time.LocalDateTime endDate) { this.endDate = endDate; }
    public void setCreatedAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setCancelledAt(java.time.LocalDateTime cancelledAt) { this.cancelledAt = cancelledAt; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }

    public static SubscriptionBuilder builder() {
        return new SubscriptionBuilder();
    }

    public static class SubscriptionBuilder {
        private Long id;
        private Long userId;
        private SubscriptionPlan plan;
        private com.demoApp.subscription.enums.SubscriptionStatus status;
        private java.time.LocalDateTime startDate;
        private java.time.LocalDateTime endDate;
        private java.time.LocalDateTime createdAt;
        private java.time.LocalDateTime cancelledAt;
        private Long paymentId;
        public SubscriptionBuilder id(Long id) { this.id = id; return this; }
        public SubscriptionBuilder userId(Long userId) { this.userId = userId; return this; }
        public SubscriptionBuilder plan(SubscriptionPlan plan) { this.plan = plan; return this; }
        public SubscriptionBuilder status(com.demoApp.subscription.enums.SubscriptionStatus status) { this.status = status; return this; }
        public SubscriptionBuilder startDate(java.time.LocalDateTime startDate) { this.startDate = startDate; return this; }
        public SubscriptionBuilder endDate(java.time.LocalDateTime endDate) { this.endDate = endDate; return this; }
        public SubscriptionBuilder createdAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public SubscriptionBuilder cancelledAt(java.time.LocalDateTime cancelledAt) { this.cancelledAt = cancelledAt; return this; }
        public SubscriptionBuilder paymentId(Long paymentId) { this.paymentId = paymentId; return this; }
        public Subscription build() {
            Subscription s = new Subscription();
            s.setId(id);
            s.setUserId(userId);
            s.setPlan(plan);
            s.setStatus(status);
            s.setStartDate(startDate);
            s.setEndDate(endDate);
            s.setCreatedAt(createdAt);
            s.setCancelledAt(cancelledAt);
            s.setPaymentId(paymentId);
            return s;
        }
    }
}
