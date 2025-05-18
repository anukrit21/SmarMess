package com.demoApp.mess.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessUpdateDTO {
    public String getName() { return name; }
    public String getDescription() { return description; }

    private String name;
    private String description;
    private String address;
    private String phone;
    private String location;
    private String contactNumber;
    @Email(message = "Invalid email format")
    private String email;
    private Boolean isActive;
    private Boolean isVerified;
    private Boolean isDeleted;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String cuisineType;
    private String openingTimeStr;
    private String closingTimeStr;
    

    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getContactNumber() {
        return contactNumber;
    }
    
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public Boolean getIsVerified() {
        return isVerified;
    }
    
    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }
    
    public Boolean getIsDeleted() {
        return isDeleted;
    }
    
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    public LocalTime getOpeningTime() {
        return openingTime;
    }
    
    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }
    
    public LocalTime getClosingTime() {
        return closingTime;
    }
    
    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }
    
    public String getCuisineType() {
        return cuisineType;
    }
    
    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }
    
    public String getOpeningTimeStr() {
        return openingTimeStr;
    }
    
    public void setOpeningTimeStr(String openingTimeStr) {
        this.openingTimeStr = openingTimeStr;
    }
    
    public String getClosingTimeStr() {
        return closingTimeStr;
    }
    
    public void setClosingTimeStr(String closingTimeStr) {
        this.closingTimeStr = closingTimeStr;
    }
}