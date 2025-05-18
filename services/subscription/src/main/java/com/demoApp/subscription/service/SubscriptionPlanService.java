package com.demoApp.subscription.service;

import com.demoApp.subscription.dto.SubscriptionPlanCreateDTO;
import com.demoApp.subscription.dto.SubscriptionPlanDTO;
import com.demoApp.subscription.dto.SubscriptionPlanUpdateDTO;
import com.demoApp.subscription.entity.SubscriptionPlan;
import com.demoApp.subscription.mapper.SubscriptionPlanMapper;
import com.demoApp.subscription.repository.SubscriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanService {
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final SubscriptionPlanMapper subscriptionPlanMapper;

    public List<SubscriptionPlanDTO> getAllPlans() {
        return subscriptionPlanRepository.findAll().stream()
                .map(subscriptionPlanMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SubscriptionPlanDTO getPlanById(Long id) {
        SubscriptionPlan plan = subscriptionPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
        return subscriptionPlanMapper.toDTO(plan);
    }

    @Transactional
    public SubscriptionPlanDTO createPlan(SubscriptionPlanCreateDTO dto) {
        SubscriptionPlan plan = subscriptionPlanMapper.toEntity(dto);
        plan = subscriptionPlanRepository.save(plan);
        return subscriptionPlanMapper.toDTO(plan);
    }

    @Transactional
    public SubscriptionPlanDTO updatePlan(Long id, SubscriptionPlanUpdateDTO dto) {
        SubscriptionPlan plan = subscriptionPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
        subscriptionPlanMapper.updateEntity(plan, dto);
        plan = subscriptionPlanRepository.save(plan);
        return subscriptionPlanMapper.toDTO(plan);
    }

    @Transactional
    public void deletePlan(Long id) {
        SubscriptionPlan plan = subscriptionPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
        subscriptionPlanRepository.delete(plan);
    }
} 