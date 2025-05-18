package com.demoApp.subscription.controller;

import com.demoApp.subscription.dto.ApiResponse;
import com.demoApp.subscription.dto.SubscriptionCreateDTO;
import com.demoApp.subscription.dto.SubscriptionDTO;
import com.demoApp.subscription.dto.SubscriptionUpdateDTO;
import com.demoApp.subscription.dto.SubscriptionAnalyticsDTO;
import com.demoApp.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<ApiResponse<SubscriptionDTO>> createSubscription(@RequestBody SubscriptionCreateDTO request) {
        SubscriptionDTO subscription = subscriptionService.createSubscription(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Subscription created successfully", subscription));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SubscriptionDTO>> updateSubscription(
            @PathVariable Long id,
            @RequestBody SubscriptionUpdateDTO request) {
        SubscriptionDTO subscription = subscriptionService.updateSubscription(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Subscription updated successfully", subscription));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<SubscriptionDTO>>> getUserSubscriptions(@PathVariable Long userId) {
        List<SubscriptionDTO> subscriptions = subscriptionService.getUserSubscriptions(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Subscriptions retrieved successfully", subscriptions));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubscriptionDTO>> getSubscription(@PathVariable Long id) {
        SubscriptionDTO subscription = subscriptionService.getSubscriptionById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Subscription retrieved successfully", subscription));
    }

    @GetMapping("/analytics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SubscriptionAnalyticsDTO>> getAnalytics(
            @RequestParam(name = "start", required = false) String start,
            @RequestParam(name = "end", required = false) String end) {

        LocalDateTime startDate = (start != null) ? LocalDateTime.parse(start) : LocalDateTime.now().minusMonths(1);
        LocalDateTime endDate = (end != null) ? LocalDateTime.parse(end) : LocalDateTime.now();

        SubscriptionAnalyticsDTO analytics = subscriptionService.getSubscriptionAnalytics(startDate, endDate);
        return ResponseEntity.ok(new ApiResponse<SubscriptionAnalyticsDTO>(true, "Analytics retrieved successfully", analytics));
    }
}
