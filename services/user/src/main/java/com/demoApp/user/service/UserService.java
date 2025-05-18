package com.demoApp.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demoApp.user.dto.UserCategoryDTO;
import com.demoApp.user.dto.UserDTO;
import com.demoApp.user.dto.UserPreferencesDTO;
import com.demoApp.user.exception.BadRequestException;
import com.demoApp.user.exception.DuplicateResourceException;
import com.demoApp.user.exception.ResourceNotFoundException;
import com.demoApp.user.model.User;
import com.demoApp.user.model.Favorite;
import com.demoApp.user.model.Rating;
import com.demoApp.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    
    @Transactional
    public User registerUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateResourceException("Email already registered");
        }
        
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setMemberType(User.UserType.CUSTOMER); // Default to CUSTOMER
        user.setVerified(false);
        user.setCreatedAt(LocalDateTime.now());
        
        User savedUser = userRepository.save(user);
        emailService.sendVerificationEmail(savedUser);
        
        return savedUser;
    }
    
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return modelMapper.map(user, UserDTO.class);
    }
    
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        // If trying to change email, check for duplicates
        if (!user.getEmail().equals(userDTO.getEmail()) && 
            userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateResourceException("Email already in use");
        }
        
        // Don't update password through this method
        user.setName(userDTO.getName());
        user.setDescription(userDTO.getDescription());
        user.setAddress(userDTO.getAddress());
        user.setMobileNumber(userDTO.getMobileNumber());
        user.setUpdatedAt(LocalDateTime.now());
        
        if (userDTO.getPreferredCategory() != null) {
            user.setPreferredCategory(userDTO.getPreferredCategory());
        }
        
        User updatedUser = userRepository.save(user);
        log.info("User updated successfully: {}", updatedUser.getId());
        return modelMapper.map(updatedUser, UserDTO.class);
    }
    
    public List<UserDTO> getAllVendors() {
        List<User> vendors = userRepository.findAllVendors();
        return vendors.stream()
                .map(vendor -> modelMapper.map(vendor, UserDTO.class))
                .collect(Collectors.toList());
    }
    
    public List<UserDTO> getUsersByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new BadRequestException("Category cannot be empty");
        }
        
        List<User> users = userRepository.findByCategory(category);
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
    
    public List<UserDTO> getUsersByPreferredCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new BadRequestException("Category cannot be empty");
        }
        
        List<User> users = userRepository.findUsersByPreferredCategory(category);
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
    
    @Transactional
    public void updateUserCategories(Long userId, UserCategoryDTO userCategoryDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        if (userCategoryDTO.getCategories() == null || userCategoryDTO.getCategories().isEmpty()) {
            throw new BadRequestException("Categories list cannot be empty");
        }
        
        user.setPreferredCategory(userCategoryDTO.getCategories());
        user.setUpdatedAt(LocalDateTime.now());
        
        userRepository.save(user);
        log.info("User categories updated for user: {}", userId);
    }
    
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        userRepository.delete(user);
        log.info("User deleted: {}", id);
    }

    @Transactional
    public UserDTO updateUserPreferences(Long id, UserPreferencesDTO preferencesDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        user.setLanguage(preferencesDTO.getLanguage());
        user.setTimezone(preferencesDTO.getTimezone());
        user.setUpdatedAt(LocalDateTime.now());
        
        User updatedUser = userRepository.save(user);
        log.info("User preferences updated for user: {}", id);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Transactional
    public UserDTO updateProfileImage(Long id, String imageUrl) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        user.setProfileImageUrl(imageUrl);
        user.setUpdatedAt(LocalDateTime.now());
        
        User updatedUser = userRepository.save(user);
        log.info("Profile image updated for user: {}", id);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Transactional
    public UserDTO addFavorite(Long userId, Long itemId, String itemType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        user.getFavorites().add(new Favorite(itemId, itemType));
        user.setUpdatedAt(LocalDateTime.now());
        
        User updatedUser = userRepository.save(user);
        log.info("Favorite added for user: {}", userId);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Transactional
    public UserDTO removeFavorite(Long userId, Long itemId, String itemType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        user.getFavorites().removeIf(fav -> 
            fav.getItemId().equals(itemId) && fav.getItemType().equals(itemType));
        user.setUpdatedAt(LocalDateTime.now());
        
        User updatedUser = userRepository.save(user);
        log.info("Favorite removed for user: {}", userId);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Transactional
    public UserDTO addRating(Long userId, Long itemId, String itemType, int rating, String review) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        user.getRatings().add(new Rating(itemId, itemType, rating, review));
        user.setUpdatedAt(LocalDateTime.now());
        
        User updatedUser = userRepository.save(user);
        log.info("Rating added for user: {}", userId);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Transactional
    public UserDTO updateRating(Long userId, Long itemId, String itemType, int rating, String review) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        user.getRatings().stream()
            .filter(r -> r.getItemId().equals(itemId) && r.getItemType().equals(itemType))
            .findFirst()
            .ifPresent(r -> {
                r.setRating(rating);
                r.setReview(review);
                r.setUpdatedAt(LocalDateTime.now());
            });
        
        user.setUpdatedAt(LocalDateTime.now());
        User updatedUser = userRepository.save(user);
        log.info("Rating updated for user: {}", userId);
        return modelMapper.map(updatedUser, UserDTO.class);
    }
}