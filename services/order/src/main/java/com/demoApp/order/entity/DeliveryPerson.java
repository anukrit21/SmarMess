package com.demoApp.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "delivery_persons")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryPersonStatus status;

    @Column(name = "shift_start_time", nullable = false)
    private LocalTime shiftStartTime;

    @Column(name = "shift_end_time", nullable = false)
    private LocalTime shiftEndTime;

    @Column(name = "max_order_capacity", nullable = false)
    private Integer maxOrderCapacity;

    @Column(name = "current_order_count", nullable = false)
    private Integer currentOrderCount;

    // @OneToMany(mappedBy = "deliveryPerson")
    // private List<Order> activeOrders;
    // Temporarily removed due to missing 'deliveryPerson' field in Order entity

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    @Column(name = "current_location", nullable = false)
    private String currentLocation;

    @Column(nullable = false)
    private Double rating;

    @Column(name = "total_deliveries", nullable = false)
    private Integer totalDeliveries;

    @Column(name = "vehicle_number", nullable = false)
    private String vehicleNumber;

    @Column(name = "vehicle_type", nullable = false)
    private String vehicleType;

    public boolean canAcceptOrder() {
        return isAvailable && 
               status == DeliveryPersonStatus.AVAILABLE && 
               currentOrderCount < maxOrderCapacity;
    }

    public void incrementOrderCount() {
        this.currentOrderCount++;
        if (this.currentOrderCount >= this.maxOrderCapacity) {
            this.status = DeliveryPersonStatus.ON_DELIVERY;
            this.isAvailable = false;
        }
    }

    public void decrementOrderCount() {
        if (this.currentOrderCount > 0) {
            this.currentOrderCount--;
            if (this.currentOrderCount < this.maxOrderCapacity) {
                this.status = DeliveryPersonStatus.AVAILABLE;
                this.isAvailable = true;
            }
        }
    }

    public void updateRating(Double newRating) {
        if (this.rating == null) {
            this.rating = newRating;
        } else {
            this.rating = (this.rating * this.totalDeliveries + newRating) / (this.totalDeliveries + 1);
        }
        this.totalDeliveries++;
    }
} 