package com.demoApp.subscription.service;

import com.demoApp.subscription.dto.PaymentRequest;
import com.demoApp.subscription.dto.SubscriptionDTO;
import com.demoApp.subscription.dto.SubscriptionCreateDTO;
import com.demoApp.subscription.dto.SubscriptionUpdateDTO;
import com.demoApp.subscription.dto.SubscriptionAnalyticsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubscriptionServiceImpl {

    private final SubscriptionService subscriptionService;
    private final PaymentService paymentService;
    
    @Autowired
    public SubscriptionServiceImpl(SubscriptionService subscriptionService, PaymentService paymentService) {
        this.subscriptionService = subscriptionService;
        this.paymentService = paymentService;
    }
    
    @Transactional
    public SubscriptionDTO createSubscription(SubscriptionCreateDTO request) {
        System.out.println("Delegating createSubscription to SubscriptionService");
        return subscriptionService.createSubscription(request);
    }
    
    @Transactional
    public SubscriptionDTO updateSubscription(Long id, SubscriptionUpdateDTO request) {
        System.out.println("Delegating updateSubscription to SubscriptionService");
        return subscriptionService.updateSubscription(id, request);
    }
    
    @Transactional(readOnly = true)
    public SubscriptionAnalyticsDTO getSubscriptionAnalytics(LocalDateTime startDate, LocalDateTime endDate) {
        System.out.println("Delegating getSubscriptionAnalytics to SubscriptionService");
        return subscriptionService.getSubscriptionAnalytics(startDate, endDate);
    }
    
    @Transactional(readOnly = true)
    public List<SubscriptionDTO> getUserSubscriptions(Long userId) {
        System.out.println("Delegating getUserSubscriptions to SubscriptionService");
        return subscriptionService.getUserSubscriptions(userId);
    }
    
    @Transactional(readOnly = true)
    public SubscriptionDTO getSubscriptionById(Long id) {
        System.out.println("Delegating getSubscriptionById to SubscriptionService");
        return subscriptionService.getSubscriptionById(id);
    }
    
    @Transactional
    public void processPayment(PaymentRequest paymentRequest) {
        System.out.println("Processing payment for subscription " + paymentRequest.getSubscriptionId());
        paymentService.processPayment(paymentRequest);
    }
}