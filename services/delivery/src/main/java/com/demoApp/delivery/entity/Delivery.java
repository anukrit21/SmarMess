package com.demoApp.delivery.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "delivery_person_id")
    private DeliveryPerson deliveryPerson;

    @ManyToOne
    @JoinColumn(name = "pickup_point_id")
    private PickupPoint pickupPoint;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "delivery_latitude")
    private Double deliveryLatitude;

    @Column(name = "delivery_longitude")
    private Double deliveryLongitude;

    @Column(name = "current_latitude")
    private Double currentLatitude;

    @Column(name = "current_longitude")
    private Double currentLongitude;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    @Column(name = "picked_up_time")
    private LocalDateTime pickedUpTime;

    @Column(name = "delivered_time")
    private LocalDateTime deliveredTime;

    @Column(name = "in_transit_at")
    private LocalDateTime inTransitAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "location_updated_at")
    private LocalDateTime locationUpdatedAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private String customerName;
    private String customerPhone;
    private String customerEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryType deliveryType;

    private LocalDateTime scheduledTime;
    private LocalDateTime acceptedTime;
    private LocalDateTime cancelledTime;
    private LocalDateTime estimatedDeliveryTime;

    private double deliveryFee;
    private double extraCharges;
    private String extraChargesReason;

    private int deliveryRating;
    private String deliveryFeedback;

    private String specialInstructions;

    // Using @Builder.Default for default value initialization for lists
    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL)
    @Builder.Default
    private List<DeliveryStatusHistory> statusHistory = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.deliveryType == DeliveryType.ON_DEMAND) {
            this.extraCharges = 50.0;
            this.extraChargesReason = "On-demand delivery surcharge";
        }
    }

    public enum DeliveryType {
        SCHEDULED, ON_DEMAND, EXPRESS
    }

    public enum DeliveryStatus {
        PENDING, ASSIGNED, ACCEPTED, PICKED_UP, IN_TRANSIT, DELIVERED, CANCELLED, FAILED
    }

    // Custom setters (only if you need special logic)
    public void setDestinationAddress(Object destinationAddress) {
        this.deliveryAddress = destinationAddress.toString();
    }

    public void setDestinationLatitude(double destinationLatitude) {
        this.deliveryLatitude = destinationLatitude;
    }

    public void setDestinationLongitude(double destinationLongitude) {
        this.deliveryLongitude = destinationLongitude;
    }

    public void setRecipientName(Object recipientName) {
        this.customerName = recipientName.toString();
    }

    public void setRecipientPhone(Object recipientPhone) {
        this.customerPhone = recipientPhone.toString();
    }

    public void setDeliveryInstructions(Object deliveryInstructions) {
        this.specialInstructions = deliveryInstructions.toString();
    }

    public void addStatusHistory(DeliveryStatus status, String notes) {
        DeliveryStatusHistory history = new DeliveryStatusHistory();
        history.setDelivery(this);
        history.setStatus(status);
        history.setTimestamp(LocalDateTime.now());
        history.setNotes(notes);
        statusHistory.add(history);
    }

    // Add missing getter/setter methods
    public Long getPickupPointId() {
        return pickupPoint != null ? pickupPoint.getId() : null;
    }

    public void setPickupPoint(PickupPoint pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public PickupPoint getPickupPoint() {
        return pickupPoint;
    }

    public DeliveryPerson getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(DeliveryPerson deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    public Long getDeliveryPersonId() {
        return deliveryPerson != null ? deliveryPerson.getId() : null;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setLocationUpdatedAt(LocalDateTime locationUpdatedAt) {
        this.locationUpdatedAt = locationUpdatedAt;
    }

    public LocalDateTime getLocationUpdatedAt() {
        return locationUpdatedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public void setPickedUpTime(LocalDateTime pickedUpTime) {
        this.pickedUpTime = pickedUpTime;
    }

    public void setInTransitTime(LocalDateTime inTransitTime) {
        this.inTransitAt = inTransitTime;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredTime = deliveredAt;
    }

    public void setCancelledAt(LocalDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public LocalDateTime getDeliveredTime() {
        return deliveredTime;
    }
}
