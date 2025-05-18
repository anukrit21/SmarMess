package com.demoApp.delivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "delivery_persons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Column(name = "vehicle_number", nullable = false)
    private String vehicleNumber;

    @Column(name = "vehicle_type", nullable = false)
    private String vehicleType;

    @Column(name = "is_available")
    private boolean isAvailable;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_zone")
    private DeliveryZone deliveryZone;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DeliveryPersonStatus status;

    @Column(name = "rating")
    private double rating;

    @Column(name = "total_deliveries")
    private int totalDeliveries;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "current_latitude")
    private Double currentLatitude;

    @Column(name = "current_longitude")
    private Double currentLongitude;

    @OneToMany(mappedBy = "deliveryPerson")
    private List<Delivery> deliveries = new ArrayList<>();

    @OneToMany(mappedBy = "deliveryPerson", cascade = CascadeType.ALL)
    private List<DeliveryFeedback> feedback = new ArrayList<>();

    @Column(name = "average_rating")
    private double averageRating;

    @Column(name = "total_ratings")
    private int totalRatings;

    @Column(name = "zone")
    @Enumerated(EnumType.STRING)
    private DeliveryZone zone;

    public enum DeliveryZone {
        NORTH,
        SOUTH,
        EAST,
        WEST,
        CENTRAL
    }

    public enum DeliveryPersonStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED,
        ON_DELIVERY,
        OFF_DUTY
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        isAvailable = true;
        isVerified = false;
        rating = 0.0;
        totalDeliveries = 0;
        status = DeliveryPersonStatus.ACTIVE;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public void setTotalRatings(int totalRatings) {
        this.totalRatings = totalRatings;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getTotalRatings() {
        return totalRatings;
    }

    public void setZone(DeliveryZone zone) {
        this.zone = zone;
    }

    public DeliveryZone getZone() {
        return zone;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { this.isAvailable = available; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }

    public LocalDateTime getLastActiveAt() { return lastLoginAt; }
    public void setLastActiveAt(LocalDateTime lastActiveAt) { this.lastLoginAt = lastActiveAt; }
}
