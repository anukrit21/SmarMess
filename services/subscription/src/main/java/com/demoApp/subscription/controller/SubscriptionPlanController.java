package com.demoApp.subscription.controller;

import com.demoApp.subscription.dto.ApiResponse;
import com.demoApp.subscription.dto.SubscriptionPlanCreateDTO;
import com.demoApp.subscription.dto.SubscriptionPlanDTO;
import com.demoApp.subscription.dto.SubscriptionPlanUpdateDTO;
import com.demoApp.subscription.service.SubscriptionPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions/plans")
@RequiredArgsConstructor
public class SubscriptionPlanController {
    private final SubscriptionPlanService subscriptionPlanService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubscriptionPlanDTO>>> getAllPlans() {
        List<SubscriptionPlanDTO> plans = subscriptionPlanService.getAllPlans();
        return ResponseEntity.ok(new ApiResponse<>(true, "Plans retrieved successfully", plans));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubscriptionPlanDTO>> getPlan(@PathVariable Long id) {
        SubscriptionPlanDTO plan = subscriptionPlanService.getPlanById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Plan retrieved successfully", plan));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SubscriptionPlanDTO>> createPlan(@RequestBody SubscriptionPlanCreateDTO plan) {
        SubscriptionPlanDTO createdPlan = subscriptionPlanService.createPlan(plan);
        return ResponseEntity.ok(new ApiResponse<>(true, "Plan created successfully", createdPlan));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SubscriptionPlanDTO>> updatePlan(
            @PathVariable Long id,
            @RequestBody SubscriptionPlanUpdateDTO plan) {
        SubscriptionPlanDTO updatedPlan = subscriptionPlanService.updatePlan(id, plan);
        return ResponseEntity.ok(new ApiResponse<>(true, "Plan updated successfully", updatedPlan));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deletePlan(@PathVariable Long id) {
        subscriptionPlanService.deletePlan(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Plan deleted successfully", null));
    }
} 