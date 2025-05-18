package com.demoApp.mess.dto;

import com.demoApp.mess.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDTO {
    private Long id;
    private Long customerId;
    private String customerName;
    private String customerPhone;
    private Long messId;
    private String messName;
    private String messPhone;
    private Long deliveryPersonId;
    private String deliveryPersonName;
    private String deliveryPersonPhone;
    private String orderReferenceId;
    private String orderReferenceType;
    private String deliveryAddress;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryPostalCode;
    private String deliveryInstructions;
    private String contactName;
    private String contactPhone;
    private Double deliveryLatitude;
    private Double deliveryLongitude;
    private Double pickupLatitude;
    private Double pickupLongitude;
    private Double currentLatitude;
    private Double currentLongitude;
    private LocalDateTime scheduledPickupTime;
    private LocalDateTime scheduledDeliveryTime;
    private LocalDateTime actualPickupTime;
    private LocalDateTime actualDeliveryTime;
    private DeliveryStatus status;
    private Integer rating;
    private String feedback;
    private String issueDescription;
    private String resolution;
    private String trackingCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Getter and Setter methods
    public Long getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public Long getMessId() {
        return messId;
    }
    
    public void setMessId(Long messId) {
        this.messId = messId;
    }
    
    public String getMessName() {
        return messName;
    }
    
    public void setMessName(String messName) {
        this.messName = messName;
    }
    
    public Long getDeliveryPersonId() {
        return deliveryPersonId;
    }
    
    public void setDeliveryPersonId(Long deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }
    
    public String getDeliveryPersonName() {
        return deliveryPersonName;
    }
    
    public void setDeliveryPersonName(String deliveryPersonName) {
        this.deliveryPersonName = deliveryPersonName;
    }
    
    public String getDeliveryPersonPhone() {
        return deliveryPersonPhone;
    }
    
    public void setDeliveryPersonPhone(String deliveryPersonPhone) {
        this.deliveryPersonPhone = deliveryPersonPhone;
    }
    
    public String getOrderReferenceId() {
        return orderReferenceId;
    }
    
    public void setOrderReferenceId(String orderReferenceId) {
        this.orderReferenceId = orderReferenceId;
    }
    
    public String getOrderReferenceType() {
        return orderReferenceType;
    }
    
    public void setOrderReferenceType(String orderReferenceType) {
        this.orderReferenceType = orderReferenceType;
    }
    
    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    
    public String getDeliveryCity() {
        return deliveryCity;
    }
    
    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }
    
    public String getDeliveryState() {
        return deliveryState;
    }
    
    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }
    
    public String getDeliveryPostalCode() {
        return deliveryPostalCode;
    }
    
    public void setDeliveryPostalCode(String deliveryPostalCode) {
        this.deliveryPostalCode = deliveryPostalCode;
    }
    
    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }
    
    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }
    
    public String getContactName() {
        return contactName;
    }
    
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    
    public String getContactPhone() {
        return contactPhone;
    }
    
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    
    public Double getDeliveryLatitude() {
        return deliveryLatitude;
    }
    
    public void setDeliveryLatitude(Double deliveryLatitude) {
        this.deliveryLatitude = deliveryLatitude;
    }
    
    public Double getDeliveryLongitude() {
        return deliveryLongitude;
    }
    
    public void setDeliveryLongitude(Double deliveryLongitude) {
        this.deliveryLongitude = deliveryLongitude;
    }
    
    public Double getPickupLatitude() {
        return pickupLatitude;
    }
    
    public void setPickupLatitude(Double pickupLatitude) {
        this.pickupLatitude = pickupLatitude;
    }
    
    public Double getPickupLongitude() {
        return pickupLongitude;
    }
    
    public void setPickupLongitude(Double pickupLongitude) {
        this.pickupLongitude = pickupLongitude;
    }
    
    public LocalDateTime getScheduledPickupTime() {
        return scheduledPickupTime;
    }
    
    public void setScheduledPickupTime(LocalDateTime scheduledPickupTime) {
        this.scheduledPickupTime = scheduledPickupTime;
    }
    
    public LocalDateTime getScheduledDeliveryTime() {
        return scheduledDeliveryTime;
    }
    
    public void setScheduledDeliveryTime(LocalDateTime scheduledDeliveryTime) {
        this.scheduledDeliveryTime = scheduledDeliveryTime;
    }
}