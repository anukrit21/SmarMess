package com.demoApp.subscription.mapper;

import com.demoApp.subscription.dto.SubscriptionPlanCreateDTO;
import com.demoApp.subscription.dto.SubscriptionPlanDTO;
import com.demoApp.subscription.dto.SubscriptionPlanUpdateDTO;
import com.demoApp.subscription.entity.SubscriptionPlan;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionPlanMapper {
    public SubscriptionPlanDTO toDTO(SubscriptionPlan plan) {
        return SubscriptionPlanDTO.builder()
                .id(plan.getId())
                .name(plan.getName())
                .description(plan.getDescription())
                .price(plan.getPrice())
                .currency(plan.getCurrency())
                .durationInMonths(plan.getDurationInMonths())
                .createdAt(plan.getCreatedAt())
                .updatedAt(plan.getUpdatedAt())
                .build();
    }

    public SubscriptionPlan toEntity(SubscriptionPlanCreateDTO dto) {
        return SubscriptionPlan.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .currency(dto.getCurrency())
                .durationInMonths(dto.getDurationInMonths())
                .build();
    }

    public void updateEntity(SubscriptionPlan plan, SubscriptionPlanUpdateDTO dto) {
        plan.setName(dto.getName());
        plan.setDescription(dto.getDescription());
        plan.setPrice(dto.getPrice());
        plan.setCurrency(dto.getCurrency());
        plan.setDurationInMonths(dto.getDurationInMonths());
    }
} 