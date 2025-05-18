package com.demoApp.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;
    private String name;
    private String description;
    private String imagePath;

    @Enumerated(EnumType.STRING)
    private UserType memberType;

    private boolean isVerified;
    private String address;
    private String mobileNumber;
    private String role;
    private String category;

    @ElementCollection
    @Builder.Default
    private List<String> preferredCategory = new ArrayList<>();

    private String language;
    private String timezone;
    private String profileImageUrl;

    @ElementCollection
    @Builder.Default
    private List<Favorite> favorites = new ArrayList<>();

    @ElementCollection
    @Builder.Default
    private List<Rating> ratings = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;

    public enum UserType {
        CUSTOMER, OWNER, ADMIN
    }
}
