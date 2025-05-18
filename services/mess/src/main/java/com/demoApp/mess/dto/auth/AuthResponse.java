package com.demoApp.mess.dto.auth;

import com.demoApp.mess.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phoneNumber;
    private String profileImageUrl;
    private String address;
    private User.Role role;  // User role
    private String token;

    // No need to map roles as we're using User.Role directly

    public static AuthResponse fromUser(User user, String token) {
        if (user == null) {
            return null;
        }

        return AuthResponse.builder()
                .id(user.getId())  // Set the id of the User entity
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhone())
                .profileImageUrl(user.getProfileImageUrl())
                .address(user.getAddress())
                .role(user.getRole())  // Use User.Role directly
                .token(token)
                .build();
    }
}
