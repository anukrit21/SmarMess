package com.demoApp.subscription.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscription_plans")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String currency;

    @Column(name = "duration_months", nullable = false)
    private Integer durationInMonths;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public java.math.BigDecimal getPrice() { return price; }
    public String getCurrency() { return currency; }
    public Integer getDurationInMonths() { return durationInMonths; }
    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    public java.time.LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(java.math.BigDecimal price) { this.price = price; }
    public void setCurrency(String currency) { this.currency = currency; }
    public void setDurationInMonths(Integer durationInMonths) { this.durationInMonths = durationInMonths; }
    public void setCreatedAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(java.time.LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static SubscriptionPlanBuilder builder() {
        return new SubscriptionPlanBuilder();
    }

    public static class SubscriptionPlanBuilder {
        private Long id;
        private String name;
        private String description;
        private java.math.BigDecimal price;
        private String currency;
        private Integer durationInMonths;
        private java.time.LocalDateTime createdAt;
        private java.time.LocalDateTime updatedAt;
        private Long createdBy;
        private Long updatedBy;

        public SubscriptionPlanBuilder id(Long id) { this.id = id; return this; }
        public SubscriptionPlanBuilder name(String name) { this.name = name; return this; }
        public SubscriptionPlanBuilder description(String description) { this.description = description; return this; }
        public SubscriptionPlanBuilder price(java.math.BigDecimal price) { this.price = price; return this; }
        public SubscriptionPlanBuilder currency(String currency) { this.currency = currency; return this; }
        public SubscriptionPlanBuilder durationInMonths(Integer durationInMonths) { this.durationInMonths = durationInMonths; return this; }
        public SubscriptionPlanBuilder createdAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public SubscriptionPlanBuilder updatedAt(java.time.LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public SubscriptionPlanBuilder createdBy(Long createdBy) { this.createdBy = createdBy; return this; }
        public SubscriptionPlanBuilder updatedBy(Long updatedBy) { this.updatedBy = updatedBy; return this; }

        public SubscriptionPlan build() {
            SubscriptionPlan plan = new SubscriptionPlan();
            plan.setId(id);
            plan.setName(name);
            plan.setDescription(description);
            plan.setPrice(price);
            plan.setCurrency(currency);
            plan.setDurationInMonths(durationInMonths);
            plan.setCreatedAt(createdAt);
            plan.setUpdatedAt(updatedAt);
            plan.setCreatedBy(createdBy);
            plan.setUpdatedBy(updatedBy);
            return plan;
        }
    }
}
