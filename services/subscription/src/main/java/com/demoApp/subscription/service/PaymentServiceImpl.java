package com.demoApp.subscription.service;

import com.demoApp.subscription.dto.PaymentRequest;
import com.demoApp.subscription.entity.Payment;
import com.demoApp.subscription.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processPayment(PaymentRequest paymentRequest) {
        System.out.println("Processing payment for subscription: " + paymentRequest.getSubscriptionId());
        validatePayment(paymentRequest);
        
        Payment payment = new Payment();
        payment.setAmount(paymentRequest.getAmount());
        payment.setCurrency(paymentRequest.getCurrency());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setStatus("PENDING");
        
        paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public void refundPayment(String paymentId) {
        System.out.println("Processing refund for payment " + paymentId);
        try {
            Long id = Long.parseLong(paymentId);
            Payment payment = paymentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Payment not found"));
            payment.setStatus("REFUNDED");
            paymentRepository.save(payment);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid payment ID format");
        }
    }

    @Override
    public void validatePayment(PaymentRequest paymentRequest) {
        System.out.println("Validating payment request for subscription " + paymentRequest.getSubscriptionId());
        if (paymentRequest.getAmount() == null || paymentRequest.getAmount().doubleValue() <= 0) {
            throw new IllegalArgumentException("Invalid payment amount");
        }
        if (paymentRequest.getCurrency() == null || paymentRequest.getCurrency().isEmpty()) {
            throw new IllegalArgumentException("Currency is required");
        }
        if (paymentRequest.getPaymentMethod() == null || paymentRequest.getPaymentMethod().isEmpty()) {
            throw new IllegalArgumentException("Payment method is required");
        }
    }
} 