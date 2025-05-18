package com.demoApp.subscription.service;

import com.demoApp.subscription.dto.UserSubscriptionDTO;
import com.demoApp.subscription.dto.UserSubscriptionResponseDTO;
import com.demoApp.subscription.entity.Subscription;
import com.demoApp.subscription.entity.User;
import com.demoApp.subscription.entity.UserSubscription;
import com.demoApp.subscription.enums.SubscriptionStatus;
import com.demoApp.subscription.exception.ResourceNotFoundException;
import com.demoApp.subscription.repository.SubscriptionRepository;
import com.demoApp.subscription.repository.UserRepository;
import com.demoApp.subscription.repository.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;

    public List<UserSubscription> getAllUserSubscriptions() {
        return userSubscriptionRepository.findAll();
    }

    public UserSubscription getUserSubscriptionById(Long id) {
        return userSubscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User subscription not found with id: " + id));
    }

    public List<UserSubscription> getUserSubscriptionsByUserId(Long userId) {
        return userSubscriptionRepository.findByUserId(userId);
    }

    public List<UserSubscription> getActiveUserSubscriptionsByUserId(Long userId) {
        return userSubscriptionRepository.findActiveSubscriptionsByUserId(userId, LocalDate.now());
    }

    @Transactional
    public UserSubscriptionResponseDTO createUserSubscription(UserSubscriptionDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));

        Subscription subscription = subscriptionRepository.findById(dto.getSubscriptionId())
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + dto.getSubscriptionId()));

        UserSubscription userSubscription = UserSubscription.builder()
                .user(user)
                .subscription(subscription)
                .startDate(dto.getStartDate() != null ? dto.getStartDate() : LocalDateTime.now())
                .endDate(dto.getEndDate() != null ? dto.getEndDate() : LocalDateTime.now().plusMonths(1))
                .status(dto.getStatus() != null ? dto.getStatus() : SubscriptionStatus.PENDING)
                .isPaymentCompleted(dto.getPaymentCompleted() != null ? dto.getPaymentCompleted() : false)
                .build();

        userSubscription = userSubscriptionRepository.save(userSubscription);
        return convertToResponseDTO(userSubscription);
    }

    @Transactional
    public UserSubscriptionResponseDTO createSubscription(Long userId, Long subscriptionId) {
        log.info("Creating subscription for user: {} and subscription: {}", userId, subscriptionId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusMonths(subscription.getPlan().getDurationInMonths());

        UserSubscription userSubscription = UserSubscription.builder()
                .user(user)
                .subscription(subscription)
                .startDate(startDate)
                .endDate(endDate)
                .isActive(true)
                .isPaymentCompleted(false)
                .build();

        userSubscription = userSubscriptionRepository.save(userSubscription);

        return UserSubscriptionResponseDTO.builder()
                .id(userSubscription.getId())
                .userId(user.getId())
                .subscriptionId(subscription.getId())
                .startDate(userSubscription.getStartDate())
                .endDate(userSubscription.getEndDate())
                .isActive(userSubscription.isActive())
                .isPaymentCompleted(userSubscription.isPaymentCompleted())
                .build();
    }

    @Transactional
    public UserSubscriptionResponseDTO getSubscription(Long userId) {
        log.info("Getting subscription for user: {}", userId);

        UserSubscription userSubscription = userSubscriptionRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new RuntimeException("No active subscription found"));

        return UserSubscriptionResponseDTO.builder()
                .id(userSubscription.getId())
                .userId(userSubscription.getUser().getId())
                .subscriptionId(userSubscription.getSubscription().getId())
                .startDate(userSubscription.getStartDate())
                .endDate(userSubscription.getEndDate())
                .isActive(userSubscription.isActive())
                .isPaymentCompleted(userSubscription.isPaymentCompleted())
                .build();
    }

    @Transactional
    public UserSubscriptionResponseDTO cancelSubscription(Long userId) {
        log.info("Cancelling subscription for user: {}", userId);

        UserSubscription userSubscription = userSubscriptionRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new RuntimeException("No active subscription found"));

        userSubscription.setActive(false);
        userSubscription.setCancelledAt(LocalDateTime.now());
        userSubscription = userSubscriptionRepository.save(userSubscription);

        return UserSubscriptionResponseDTO.builder()
                .id(userSubscription.getId())
                .userId(userSubscription.getUser().getId())
                .subscriptionId(userSubscription.getSubscription().getId())
                .startDate(userSubscription.getStartDate())
                .endDate(userSubscription.getEndDate())
                .isActive(userSubscription.isActive())
                .isPaymentCompleted(userSubscription.isPaymentCompleted())
                .build();
    }

    @Transactional
    public UserSubscriptionResponseDTO updateUserSubscription(Long id, UserSubscriptionDTO dto) {
        UserSubscription userSubscription = userSubscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User subscription not found with id: " + id));

        if (dto.getStartDate() != null) {
            userSubscription.setStartDate(dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            userSubscription.setEndDate(dto.getEndDate());
        }
        if (dto.getStatus() != null) {
            userSubscription.setStatus(dto.getStatus());
        }
        if (dto.getPaymentCompleted() != null) {
            userSubscription.setPaymentCompleted(dto.getPaymentCompleted());
        }

        userSubscription = userSubscriptionRepository.save(userSubscription);
        return convertToResponseDTO(userSubscription);
    }

    @Transactional
    public void deleteUserSubscription(Long id) {
        UserSubscription userSubscription = userSubscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User subscription not found with id: " + id));

        userSubscriptionRepository.delete(userSubscription);
    }

    /**
     * Check for expired subscriptions daily at midnight and update their status
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void updateExpiredSubscriptions() {
        List<UserSubscription> expiredSubscriptions = userSubscriptionRepository.findExpiredSubscriptions(LocalDate.now());

        for (UserSubscription subscription : expiredSubscriptions) {
            subscription.setStatus(SubscriptionStatus.EXPIRED);
            userSubscriptionRepository.save(subscription);
        }
    }

    /**
     * Convert a UserSubscription entity to a Response DTO
     */
    public UserSubscriptionResponseDTO convertToResponseDTO(UserSubscription userSubscription) {
        return UserSubscriptionResponseDTO.builder()
                .id(userSubscription.getId())
                .userId(userSubscription.getUser().getId())
                .subscriptionId(userSubscription.getSubscription().getId())
                .startDate(userSubscription.getStartDate())
                .endDate(userSubscription.getEndDate())
                .status(userSubscription.getStatus())
                .isPaymentCompleted(userSubscription.isPaymentCompleted())
                .build();
    }
    
    /**
     * Cancel a user subscription by ID
     */
    @Transactional
    public void cancelUserSubscription(Long id) {
        UserSubscription userSubscription = userSubscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User subscription not found with id: " + id));
        
        if (!userSubscription.isActive()) {
            throw new IllegalStateException("Subscription is already inactive");
        }
        
        userSubscription.setActive(false);
        userSubscription.setCancelledAt(LocalDateTime.now());
        userSubscription.setStatus(SubscriptionStatus.CANCELLED);
        userSubscriptionRepository.save(userSubscription);
    }

    public List<UserSubscriptionResponseDTO> convertToResponseDTOs(List<UserSubscription> userSubscriptions) {
        return userSubscriptions.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }
}
