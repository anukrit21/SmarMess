package com.demoApp.subscription.controller;

import com.demoApp.subscription.dto.ApiResponse;
import com.demoApp.subscription.dto.UserSubscriptionDTO;
import com.demoApp.subscription.dto.UserSubscriptionResponseDTO;
import com.demoApp.subscription.entity.UserSubscription;
import com.demoApp.subscription.service.UserSubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class UserSubscriptionController {

    private final UserSubscriptionService userSubscriptionService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserSubscriptionResponseDTO>> getAllUserSubscriptions() {
        List<UserSubscription> subscriptions = userSubscriptionService.getAllUserSubscriptions();
        return ResponseEntity.ok(userSubscriptionService.convertToResponseDTOs(subscriptions));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserSubscriptionResponseDTO> getUserSubscriptionById(@PathVariable Long id) {
        UserSubscription subscription = userSubscriptionService.getUserSubscriptionById(id);
        return ResponseEntity.ok(userSubscriptionService.convertToResponseDTO(subscription));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<UserSubscriptionResponseDTO>> getUserSubscriptionsByUserId(@PathVariable Long userId) {
        List<UserSubscription> subscriptions = userSubscriptionService.getUserSubscriptionsByUserId(userId);
        return ResponseEntity.ok(userSubscriptionService.convertToResponseDTOs(subscriptions));
    }

    @GetMapping("/user/{userId}/active")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<UserSubscriptionResponseDTO>> getActiveUserSubscriptionsByUserId(@PathVariable Long userId) {
        List<UserSubscription> subscriptions = userSubscriptionService.getActiveUserSubscriptionsByUserId(userId);
        return ResponseEntity.ok(userSubscriptionService.convertToResponseDTOs(subscriptions));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserSubscriptionResponseDTO> createUserSubscription(@Valid @RequestBody UserSubscriptionDTO userSubscriptionDTO) {
        UserSubscriptionResponseDTO response = userSubscriptionService.createUserSubscription(userSubscriptionDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserSubscriptionResponseDTO> updateUserSubscription(
            @PathVariable Long id,
            @Valid @RequestBody UserSubscriptionDTO userSubscriptionDTO) {
        UserSubscriptionResponseDTO response = userSubscriptionService.updateUserSubscription(id, userSubscriptionDTO);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> cancelUserSubscription(@PathVariable Long id) {
        try {
            userSubscriptionService.cancelUserSubscription(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "User subscription cancelled successfully", null));
        } catch (IllegalStateException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteUserSubscription(@PathVariable Long id) {
        userSubscriptionService.deleteUserSubscription(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "User subscription deleted successfully", null));
    }

    @PostMapping("/users/{userId}/subscriptions/{subscriptionId}")
    public ResponseEntity<UserSubscriptionResponseDTO> createSubscription(
            @PathVariable Long userId,
            @PathVariable Long subscriptionId) {
        // Using System.out.println instead of log to avoid log-related issues
        System.out.println("Creating subscription for user: " + userId + " and subscription: " + subscriptionId);
        return ResponseEntity.ok(userSubscriptionService.createSubscription(userId, subscriptionId));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserSubscriptionResponseDTO> getSubscription(@PathVariable Long userId) {
        // Using System.out.println instead of log to avoid log-related issues
        System.out.println("Getting subscription for user: " + userId);
        return ResponseEntity.ok(userSubscriptionService.getSubscription(userId));
    }

    @PostMapping("/users/{userId}/cancel")
    public ResponseEntity<UserSubscriptionResponseDTO> cancelSubscription(@PathVariable Long userId) {
        // Using System.out.println instead of log to avoid log-related issues
        System.out.println("Cancelling subscription for user: " + userId);
        return ResponseEntity.ok(userSubscriptionService.cancelSubscription(userId));
    }
}
