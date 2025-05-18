package com.demoApp.admin.service;

import com.demoApp.admin.dto.UserDTO;
import com.demoApp.admin.entity.User;
import com.demoApp.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private UserDTO convertToDTO(User user) {
    return UserDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .firstName(user.getFirstName()) 
            .lastName(user.getLastName())  
            .phoneNumber(user.getPhone()) 
            .role(user.getRole().name())  
            .status(user.getStatus().name())  
            .createdAt(user.getCreatedAt())
            .build();
}

}
