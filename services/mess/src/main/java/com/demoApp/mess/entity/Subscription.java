package com.demoApp.mess.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    
    public enum SubscriptionType {
        DAILY, WEEKLY, MONTHLY, CUSTOM
    }
    
    public enum MealType {
        BREAKFAST, LUNCH, DINNER, ALL
    }
    
    public enum Status {
        ACTIVE, INACTIVE, PENDING, EXPIRED
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getDurationDays() { return durationDays; }
    public void setDurationDays(Integer durationDays) { this.durationDays = durationDays; }
    public SubscriptionType getType() { return type; }
    public void setType(SubscriptionType type) { this.type = type; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public Mess getMess() { return mess; }
    public void setMess(Mess mess) { this.mess = mess; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Integer getMealsPerWeek() { return mealsPerWeek; }
    public void setMealsPerWeek(Integer mealsPerWeek) { this.mealsPerWeek = mealsPerWeek; }
    public String getDeliveryDays() { return deliveryDays; }
    public void setDeliveryDays(String deliveryDays) { this.deliveryDays = deliveryDays; }
    public MealType getMealType() { return mealType; }
    public void setMealType(MealType mealType) { this.mealType = mealType; }
    public String getDietaryOptions() { return dietaryOptions; }
    public void setDietaryOptions(String dietaryOptions) { this.dietaryOptions = dietaryOptions; }
    public Integer getMealPerPortion() { return mealPerPortion; }
    public void setMealPerPortion(Integer mealPerPortion) { this.mealPerPortion = mealPerPortion; }
    public String getOffice() { return office; }
    public void setOffice(String office) { this.office = office; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column(name = "duration_days", nullable = false)
    private Integer durationDays;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionType type;
    
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;
    
    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mess_id", nullable = false)
    private Mess mess;
    
    @Column(name = "user_id")
    private Long userId;
    
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
    
    // New fields
    private String imageUrl;
    
    // Fields for subscription details
    private Integer mealsPerWeek;
    
    private String deliveryDays;
    
    @Enumerated(EnumType.STRING)
    private MealType mealType;
    
    private String dietaryOptions; // Veg, Non-veg, etc.
    
    private Integer mealPerPortion;
    
    private String office;
    
    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Builder.Default
    private String mealTypes = "";

    public String getMealTypes() {
        return mealTypes;
    }

    public void setMealTypes(String mealTypes) {
        this.mealTypes = mealTypes != null ? mealTypes : "";
    }

}
