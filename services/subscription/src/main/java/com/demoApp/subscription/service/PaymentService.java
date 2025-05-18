package com.demoApp.subscription.service;

import com.demoApp.subscription.dto.PaymentRequest;

public interface PaymentService {
    void processPayment(PaymentRequest paymentRequest);
    void refundPayment(String paymentId);
    void validatePayment(PaymentRequest paymentRequest);
} 