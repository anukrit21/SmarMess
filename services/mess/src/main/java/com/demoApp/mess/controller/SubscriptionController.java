package com.demoApp.mess.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.demoApp.mess.dto.ApiResponse;
import com.demoApp.mess.dto.SubscriptionDTO;
import com.demoApp.mess.entity.Subscription;
import com.demoApp.mess.service.SubscriptionService;

@RestController
@RequestMapping("/api/v1/subscriptions")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasRole('MESS') and @userSecurity.isMessOwnerOrAdmin(#subscriptionDTO.messId)")
    public ResponseEntity<SubscriptionDTO> createSubscription(@Valid @RequestBody SubscriptionDTO subscriptionDTO) {
        Subscription subscription = subscriptionService.createSubscription(subscriptionDTO);
        SubscriptionDTO resultDTO = modelMapper.map(subscription, SubscriptionDTO.class);
        return new ResponseEntity<>(resultDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MESS') and @userSecurity.isMessOwnerOrAdmin(#subscriptionDTO.messId)")
    public ResponseEntity<SubscriptionDTO> updateSubscription(
            @PathVariable Long id, 
            @RequestBody SubscriptionDTO subscriptionDTO) {
        Subscription subscription = subscriptionService.updateSubscription(id, subscriptionDTO);
        // Convert Subscription to SubscriptionDTO if needed, or modify the service to return DTO
        SubscriptionDTO resultDTO = modelMapper.map(subscription, SubscriptionDTO.class);
        return ResponseEntity.ok(resultDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MESS') and @userSecurity.isMessOwnerOrAdmin(#messId)")
    public ResponseEntity<ApiResponse> deleteSubscription(
            @PathVariable Long id,
            @RequestParam(required = false) Long messId) {
        
        if (messId == null) {
            messId = subscriptionService.getMessIdBySubscriptionId(id);
        }
        
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.ok(new ApiResponse(true, "Subscription deleted successfully"));
    }

    @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('MESS') and @userSecurity.isMessOwnerOrAdmin(#messId)")
    public ResponseEntity<ApiResponse> uploadSubscriptionImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image,
            @RequestParam(required = false) Long messId) {
        
        if (messId == null) {
            messId = subscriptionService.getMessIdBySubscriptionId(id);
        }
        
        String imageUrl = subscriptionService.uploadAndSetSubscriptionImage(id, image);
        
        return ResponseEntity.ok(new ApiResponse(true, "Subscription image uploaded successfully", imageUrl));
    }
    
    @DeleteMapping("/{id}/image")
    @PreAuthorize("hasRole('MESS') and @userSecurity.isMessOwnerOrAdmin(#messId)")
    public ResponseEntity<ApiResponse> deleteSubscriptionImage(
            @PathVariable Long id,
            @RequestParam(required = false) Long messId) {
        
        if (messId == null) {
            messId = subscriptionService.getMessIdBySubscriptionId(id);
        }
        
        boolean deleted = subscriptionService.deleteSubscriptionImage(id);
        
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse(true, "Subscription image deleted successfully"));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "No image to delete"));
        }
    }
}
