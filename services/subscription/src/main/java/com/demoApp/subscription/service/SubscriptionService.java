package com.demoApp.subscription.service;

import com.demoApp.subscription.client.NotificationRequest;
import com.demoApp.subscription.client.NotificationService;
import com.demoApp.subscription.dto.SubscriptionAnalyticsDTO;
import com.demoApp.subscription.dto.SubscriptionCreateDTO;
import com.demoApp.subscription.dto.SubscriptionDTO;
import com.demoApp.subscription.dto.SubscriptionUpdateDTO;
import com.demoApp.subscription.entity.Subscription;
import com.demoApp.subscription.entity.SubscriptionPlan;
import com.demoApp.subscription.enums.SubscriptionStatus;
import com.demoApp.subscription.exception.ResourceNotFoundException;
import com.demoApp.subscription.mapper.SubscriptionMapper;
import com.demoApp.subscription.repository.SubscriptionPlanRepository;
import com.demoApp.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionPlanRepository planRepository;
    private final NotificationService notificationService;
    private final SubscriptionMapper subscriptionMapper;

    @Transactional(readOnly = true)
    public SubscriptionAnalyticsDTO getSubscriptionAnalytics(LocalDateTime startDate, LocalDateTime endDate) {
        // Get total subscriptions in date range
        long totalSubscriptions = subscriptionRepository.countByCreatedAtBetween(startDate, endDate);

        // Get active subscriptions
        long activeSubscriptions = subscriptionRepository.findByStatus(SubscriptionStatus.ACTIVE).size();

        // Get revenue in date range
        BigDecimal revenue = subscriptionRepository.sumAmountByCreatedAtBetween(startDate, endDate);
        if (revenue == null) {
            revenue = BigDecimal.ZERO;
        }

        // Get plan distribution
        Map<String, Long> planDistribution = subscriptionRepository.countByPlanAndCreatedAtBetween(startDate, endDate)
                .stream()
                .collect(Collectors.toMap(
                    result -> ((SubscriptionPlan) result[0]).getName(),
                    result -> (Long) result[1]
                ));

        // Get status distribution
        Map<String, Long> statusDistribution = subscriptionRepository.countByStatusAndCreatedAtBetween(startDate, endDate)
                .stream()
                .collect(Collectors.toMap(
                    result -> ((SubscriptionStatus) result[0]).name(),
                    result -> (Long) result[1]
                ));

        // Calculate churn rate (cancelled subscriptions / total subscriptions)
        Long cancelledSubscriptions = statusDistribution.getOrDefault("CANCELLED", 0L);
        Double churnRate = totalSubscriptions > 0 ? 
            (cancelledSubscriptions.doubleValue() / totalSubscriptions) : 0.0;

        // Create and return the analytics DTO
        return SubscriptionAnalyticsDTO.builder()
                .totalSubscriptions(totalSubscriptions)
                .activeSubscriptions(activeSubscriptions)
                .revenue(revenue)
                .planDistribution(planDistribution)
                .statusDistribution(statusDistribution)
                .churnRate(churnRate)
                .build();
    }

    @Transactional
    public SubscriptionDTO createSubscription(SubscriptionCreateDTO request) {
        SubscriptionPlan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("SubscriptionPlan", "id", request.getPlanId()));

        Subscription subscription = subscriptionMapper.toEntity(request);
        subscription.setPlan(plan);
        subscription.setStatus(SubscriptionStatus.PENDING);
        subscription.setStartDate(LocalDateTime.now());
        subscription.setEndDate(LocalDateTime.now().plusMonths(plan.getDurationInMonths()));

        subscription = subscriptionRepository.save(subscription);

        // Send notification
        notificationService.sendNotification(NotificationRequest.builder()
                .userId(request.getUserId())
                .type("SUBSCRIPTION_CREATED")
                .title("Subscription Created")
                .message("Your subscription to " + plan.getName() + " has been created")
                .build());

        return subscriptionMapper.toDTO(subscription);
    }

    @Transactional
    public SubscriptionDTO updateSubscription(Long id, SubscriptionUpdateDTO request) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription", "id", id));

        if (request.getPlanId() != null) {
            SubscriptionPlan newPlan = planRepository.findById(request.getPlanId())
                    .orElseThrow(() -> new ResourceNotFoundException("SubscriptionPlan", "id", request.getPlanId()));

            // Calculate price difference
            BigDecimal priceDifference = newPlan.getPrice().subtract(subscription.getPlan().getPrice());

            if (priceDifference.compareTo(BigDecimal.ZERO) > 0) {
                // Handle price difference payment
                // This would typically involve calling a payment service
            }

            subscription.setPlan(newPlan);
            subscription.setEndDate(LocalDateTime.now().plusMonths(newPlan.getDurationInMonths()));

            // Send notification
            notificationService.sendNotification(NotificationRequest.builder()
                    .userId(subscription.getUserId())
                    .type("SUBSCRIPTION_UPDATED")
                    .title("Subscription Updated")
                    .message("Your subscription has been updated to " + newPlan.getName())
                    .build());
        }

        subscriptionMapper.updateEntity(subscription, request);
        subscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDTO(subscription);
    }

    @Transactional(readOnly = true)
    public List<SubscriptionDTO> getUserSubscriptions(Long userId) {
        return subscriptionRepository.findByUserId(userId).stream()
                .map(subscriptionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubscriptionDTO getSubscriptionById(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription", "id", id));
        return subscriptionMapper.toDTO(subscription);
    }

    public Map<String, Object> getSubscriptionAnalytics() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        Map<SubscriptionStatus, Long> statusCounts = subscriptions.stream()
                .collect(Collectors.groupingBy(Subscription::getStatus, Collectors.counting()));

        BigDecimal totalRevenue = subscriptions.stream()
                .filter(s -> s.getStatus() == SubscriptionStatus.ACTIVE)
                .map(s -> s.getPlan().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> analytics = Map.of(
                "totalSubscriptions", subscriptions.size(),
                "activeSubscriptions", statusCounts.getOrDefault(SubscriptionStatus.ACTIVE, 0L),
                "cancelledSubscriptions", statusCounts.getOrDefault(SubscriptionStatus.CANCELLED, 0L),
                "totalRevenue", totalRevenue
        );

        return analytics;
    }
} 