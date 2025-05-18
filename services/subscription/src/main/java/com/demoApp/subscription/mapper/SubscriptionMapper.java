package com.demoApp.subscription.mapper;

import com.demoApp.subscription.dto.SubscriptionCreateDTO;
import com.demoApp.subscription.dto.SubscriptionDTO;
import com.demoApp.subscription.dto.SubscriptionUpdateDTO;
import com.demoApp.subscription.entity.Subscription;
import com.demoApp.subscription.enums.SubscriptionStatus;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {
    public SubscriptionDTO toDTO(Subscription subscription) {
        return SubscriptionDTO.builder()
                .id(subscription.getId())
                .userId(subscription.getUserId())
                .plan(subscription.getPlan())
                .status(subscription.getStatus())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .createdAt(subscription.getCreatedAt())
                .cancelledAt(subscription.getCancelledAt())
                .paymentId(subscription.getPaymentId())
                .build();
    }

    public Subscription toEntity(SubscriptionCreateDTO dto) {
        Subscription subscription = new Subscription();
        subscription.setUserId(dto.getUserId());
        // Set default status to PENDING
        subscription.setStatus(SubscriptionStatus.PENDING);
        return subscription;
    }

    public void updateEntity(Subscription subscription, SubscriptionUpdateDTO dto) {
        if (dto.getStatus() != null) {
            subscription.setStatus(dto.getStatus());
            if (dto.getStatus().equals(SubscriptionStatus.CANCELLED)) {
                subscription.setCancelledAt(java.time.LocalDateTime.now());
            }
        }
        if (dto.getEndDate() != null) {
            subscription.setEndDate(dto.getEndDate());
        }
    }
} 