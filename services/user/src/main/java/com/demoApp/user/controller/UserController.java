package com.demoApp.user.controller;

import com.demoApp.user.dto.UserCategoryDTO;
import com.demoApp.user.dto.UserDTO;
import com.demoApp.user.dto.UserPreferencesDTO;
import com.demoApp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(modelMapper.map(userService.registerUser(userDTO), UserDTO.class));
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("@userAuthorizationService.isUserAuthorized(#id)")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("@userAuthorizationService.isUserAuthorized(#id)")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id, 
            @Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }
    
    @GetMapping("/vendors")
    public ResponseEntity<List<UserDTO>> getAllVendors() {
        return ResponseEntity.ok(userService.getAllVendors());
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<UserDTO>> getUsersByCategory(@PathVariable String category) {
        return ResponseEntity.ok(userService.getUsersByCategory(category));
    }
    
    @GetMapping("/preferred-category/{category}")
    public ResponseEntity<List<UserDTO>> getUsersByPreferredCategory(@PathVariable String category) {
        return ResponseEntity.ok(userService.getUsersByPreferredCategory(category));
    }
    
    @PutMapping("/{id}/categories")
    @PreAuthorize("@userAuthorizationService.isUserAuthorized(#id)")
    public ResponseEntity<Void> updateUserCategories(
            @PathVariable Long id,
            @Valid @RequestBody UserCategoryDTO userCategoryDTO) {
        userService.updateUserCategories(id, userCategoryDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/preferences")
    public ResponseEntity<UserDTO> updateUserPreferences(
            @PathVariable Long id,
            @RequestBody UserPreferencesDTO preferencesDTO) {
        return ResponseEntity.ok(userService.updateUserPreferences(id, preferencesDTO));
    }

    @PutMapping("/{id}/profile-image")
    public ResponseEntity<UserDTO> updateProfileImage(
            @PathVariable Long id,
            @RequestParam String imageUrl) {
        return ResponseEntity.ok(userService.updateProfileImage(id, imageUrl));
    }

    @PostMapping("/{userId}/favorites")
    public ResponseEntity<UserDTO> addFavorite(
            @PathVariable Long userId,
            @RequestParam Long itemId,
            @RequestParam String itemType) {
        return ResponseEntity.ok(userService.addFavorite(userId, itemId, itemType));
    }

    @DeleteMapping("/{userId}/favorites")
    public ResponseEntity<UserDTO> removeFavorite(
            @PathVariable Long userId,
            @RequestParam Long itemId,
            @RequestParam String itemType) {
        return ResponseEntity.ok(userService.removeFavorite(userId, itemId, itemType));
    }

    @PostMapping("/{userId}/ratings")
    public ResponseEntity<UserDTO> addRating(
            @PathVariable Long userId,
            @RequestParam Long itemId,
            @RequestParam String itemType,
            @RequestParam int rating,
            @RequestParam(required = false) String review) {
        return ResponseEntity.ok(userService.addRating(userId, itemId, itemType, rating, review));
    }

    @PutMapping("/{userId}/ratings")
    public ResponseEntity<UserDTO> updateRating(
            @PathVariable Long userId,
            @RequestParam Long itemId,
            @RequestParam String itemType,
            @RequestParam int rating,
            @RequestParam(required = false) String review) {
        return ResponseEntity.ok(userService.updateRating(userId, itemId, itemType, rating, review));
    }
}
