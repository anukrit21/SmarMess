package com.demoApp.mess.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessCreateDTO {
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Password is required")
    private String password;
    
    private String contactNumber;
    
    private String location;
    
    private String description;
    
    private String cuisineType;
    
    private String openingTime;
    
    private String closingTime;
    

    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getContactNumber() {
        return contactNumber;
    }
    
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCuisineType() {
        return cuisineType;
    }
    
    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }
    
    public String getOpeningTime() {
        return openingTime;
    }
    
    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }
    
    public String getClosingTime() {
        return closingTime;
    }
    
    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }
}
