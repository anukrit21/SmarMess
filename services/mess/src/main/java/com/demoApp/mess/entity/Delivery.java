package com.demoApp.mess.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.demoApp.mess.enums.DeliveryStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {
    public void setOrderReferenceId(String orderReferenceId) { this.orderReferenceId = orderReferenceId; }
    public void setOrderReferenceType(String orderReferenceType) { this.orderReferenceType = orderReferenceType; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    public void setDeliveryCity(String deliveryCity) { this.deliveryCity = deliveryCity; }
    public void setDeliveryState(String deliveryState) { this.deliveryState = deliveryState; }
    public void setDeliveryPostalCode(String deliveryPostalCode) { this.deliveryPostalCode = deliveryPostalCode; }
    public void setDeliveryInstructions(String deliveryInstructions) { this.deliveryInstructions = deliveryInstructions; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public void setDeliveryLatitude(Double deliveryLatitude) { this.deliveryLatitude = deliveryLatitude; }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Long getId() {
        return id;
    }
    
    // Tracking code (unique identifier for customers to track their delivery)
    @Column(unique = true)
    private String trackingCode;
    
    // Reference to the order - could be a subscription, direct order, or other
    @Column(name = "order_reference_id", nullable = false)
    private String orderReferenceId;
    
    @Column(name = "order_reference_type", nullable = false)
    private String orderReferenceType; // "SUBSCRIPTION", "MENU", etc.
    
    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;
    
    public User getCustomer() {
        return customer;
    }
    
    public void setCustomer(User customer) {
        this.customer = customer;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mess_id", nullable = false)
    private User mess;
    
    public User getMess() {
        return mess;
    }
    
    public void setMess(User user) {
        this.mess = user;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_person_id")
    private DeliveryPerson deliveryPerson;
    
    public DeliveryPerson getDeliveryPerson() {
        return deliveryPerson;
    }
    
    public void setDeliveryPerson(DeliveryPerson deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }
    
    // Delivery location
    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;
    
    @Column(name = "delivery_city")
    private String deliveryCity;
    
    @Column(name = "delivery_state")
    private String deliveryState;
    
    @Column(name = "delivery_postal_code")
    private String deliveryPostalCode;
    
    @Column(name = "delivery_instructions", columnDefinition = "TEXT")
    private String deliveryInstructions;
    
    // Contact information
    @Column(name = "contact_name", nullable = false)
    private String contactName;
    
    @Column(name = "contact_phone", nullable = false)
    private String contactPhone;
    
    // Coordinates for pickup and delivery
    @Column(name = "pickup_latitude")
    private Double pickupLatitude;
    
    public void setPickupLatitude(Double pickupLatitude) {
        this.pickupLatitude = pickupLatitude;
    }
    
    @Column(name = "pickup_longitude")
    private Double pickupLongitude;
    
    public void setPickupLongitude(Double pickupLongitude) {
        this.pickupLongitude = pickupLongitude;
    }
    
    @Column(name = "delivery_latitude")
    private Double deliveryLatitude;
    
    @Column(name = "delivery_longitude")
    private Double deliveryLongitude;
    
    public void setDeliveryLongitude(Double deliveryLongitude) {
        this.deliveryLongitude = deliveryLongitude;
    }
    
    // Current location (for tracking)
    @Column(name = "current_latitude")
    private Double currentLatitude;
    
    public Double getCurrentLatitude() {
        return currentLatitude;
    }
    
    public void setCurrentLatitude(Double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }
    
    @Column(name = "current_longitude")
    private Double currentLongitude;
    
    public Double getCurrentLongitude() {
        return currentLongitude;
    }
    
    public void setCurrentLongitude(Double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }
    
    // Scheduled and actual timestamps
    @Column(name = "scheduled_pickup_time")
    private LocalDateTime scheduledPickupTime;
    
    public void setScheduledPickupTime(LocalDateTime scheduledPickupTime) {
        this.scheduledPickupTime = scheduledPickupTime;
    }
    
    @Column(name = "scheduled_delivery_time")
    private LocalDateTime scheduledDeliveryTime;
    
    public void setScheduledDeliveryTime(LocalDateTime scheduledDeliveryTime) {
        this.scheduledDeliveryTime = scheduledDeliveryTime;
    }
    
    @Column(name = "actual_pickup_time")
    private LocalDateTime actualPickupTime;
    
    public void setActualPickupTime(LocalDateTime time) {
        this.actualPickupTime = time;
    }
    
    @Column(name = "actual_delivery_time")
    private LocalDateTime actualDeliveryTime;
    
    public void setActualDeliveryTime(LocalDateTime time) {
        this.actualDeliveryTime = time;
    }
    
    // Status
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private DeliveryStatus status = DeliveryStatus.PENDING;
    
    public DeliveryStatus getStatus() {
        return status;
    }
    
    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }
    
    // Feedback from customer
    @Column(name = "rating")
    private Integer rating;
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;
    
    public String getFeedback() {
        return feedback;
    }
    
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    
    // Issue handling
    @Column(name = "issue_description", columnDefinition = "TEXT")
    private String issueDescription;
    
    public String getIssueDescription() {
        return issueDescription;
    }
    
    public void setIssueDescription(String description) {
        this.issueDescription = description;
    }
    
    @Column(name = "resolution", columnDefinition = "TEXT")
    private String resolution;
    
    public String getResolution() {
        return resolution;
    }
    
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
    
    // Audit fields
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "created_by", updatable = false)
    private Long createdBy;
    
    public void setCreatedBy(Long id) {
        this.createdBy = id;
    }
    
    @Column(name = "updated_by")
    private Long updatedBy;
    
    public void setUpdatedBy(Long id) {
        this.updatedBy = id;
    }
    
    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;
    
    // Lifecycle callback - generate tracking code if not present
    @PrePersist
    protected void onCreate() {
        if (trackingCode == null || trackingCode.isEmpty()) {
            // Generate a random tracking code with format: PREFIX-RANDOM-TIMESTAMP
            String prefix = "DLV";
            String randomPart = Long.toString(Math.abs(System.nanoTime() % 10000));
            String timestamp = Long.toString(System.currentTimeMillis() / 1000).substring(6);
            
            this.trackingCode = prefix + "-" + randomPart + "-" + timestamp;
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = DeliveryStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
