package com.demoApp.order.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "messes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "owner_id", nullable = false)
    private Long ownerId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;
    
    @Column
    private String imageUrl;
    
    private String description;
    
    @Column
    private String openingHours;
    
    @Column
    private String closingHours;

    @Column(name = "total_orders")
    private Long totalOrders;

    @Column(name = "total_revenue")
    private BigDecimal totalRevenue;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 