package com.demoApp.mess.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "delivery_persons")
public class DeliveryPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "mess_id", nullable = false)
    private Mess mess;

    public Mess getMess() {
        return mess;
    }
    
    public void setMess(Mess mess) {
        this.mess = mess;
    }
    
    // Method to set Mess from User object
    public void setMess(User user) {
        if (user == null) {
            this.mess = null;
            return;
        }
        
        // In a real implementation, you would fetch the Mess associated with this User
        // This is a simplified example that creates a new Mess object
        Mess mess = new Mess();
        // Note: We don't set the ID as it's auto-generated
        mess.setName(user.getName());
        mess.setEmail(user.getEmail());
        mess.setContactNumber(user.getPhone());
        
        this.mess = mess;
    }

    @Column(name = "name", nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "phone", nullable = false)
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    @Column(name = "vehicle_type")
    private String vehicleType;

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

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

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Column(name = "average_rating")
    private Double averageRating;

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    @Column(name = "total_ratings")
    private Integer totalRatings;

    public Integer getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(int count) {
        this.totalRatings = count;
    }

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Column(name = "created_by")
    private Long createdBy;

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "updated_by")
    private Long updatedBy;

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long id) {
        this.updatedBy = id;
    }

    @Column(name = "id_type")
    private String idType;

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    @Column(name = "id_number")
    private String idNumber;
    
    public String getIdNumber() {
        return idNumber;
    }
    
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Column(name = "id_proof_url")
    private String idProofUrl;
    
    public String getIdProofUrl() {
        return idProofUrl;
    }

    @Column(name = "profile_image_url")
    private String profileImageUrl;
    
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    @Column(name = "id_proof_image_url")
    private String idProofImageUrl;
    
    public String getIdProofImageUrl() {
        return idProofImageUrl;
    }
    
    public void setIdProofImageUrl(String idProofImageUrl) {
        this.idProofImageUrl = idProofImageUrl;
    }

    @Column(name = "address")
    private String address;
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "city")
    private String city;
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "state")
    private String state;
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "postal_code")
    private String postalCode;
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Column(name = "last_latitude")
    private Double lastLatitude;
    
    public Double getLastLatitude() {
        return lastLatitude;
    }
    
    public void setLastLatitude(Double lastLatitude) {
        this.lastLatitude = lastLatitude;
    }

    @Column(name = "last_longitude")
    private Double lastLongitude;
    
    public Double getLastLongitude() {
        return lastLongitude;
    }
    
    public void setLastLongitude(Double lastLongitude) {
        this.lastLongitude = lastLongitude;
    }

    @Column(name = "last_location_update_time")
    private LocalDateTime lastLocationUpdateTime;
    
    public LocalDateTime getLastLocationUpdateTime() {
        return lastLocationUpdateTime;
    }
    
    public void setLastLocationUpdateTime(LocalDateTime lastLocationUpdateTime) {
        this.lastLocationUpdateTime = lastLocationUpdateTime;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isActive == null) {
            isActive = true;
        }
        if (totalRatings == null) {
            totalRatings = 0;
        }
        if (averageRating == null) {
            averageRating = 0.0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void setIdProofUrl(String fileName) {
        this.idProofImageUrl = fileName;
    }
}