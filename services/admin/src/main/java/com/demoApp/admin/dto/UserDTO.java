package com.demoApp.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data  // Lombok annotation to generate getter/setter methods, equals, hashCode, and toString
@Builder  // Lombok annotation to automatically generate the builder pattern
@NoArgsConstructor  // Lombok annotation to generate a no-arguments constructor
@AllArgsConstructor // Lombok annotation to generate an all-arguments constructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String role;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private String status;
    private String profilePicture;
    private String address;
    private String city;
    private String country;
    private String postalCode;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private String name;
    private String phone;
    private boolean locked;
}
