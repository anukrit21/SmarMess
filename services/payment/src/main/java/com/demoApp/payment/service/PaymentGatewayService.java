package com.demoApp.payment.service;

import com.demoApp.payment.dto.*;
import com.demoApp.payment.exception.PaymentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentGatewayService {


    private final StripeService stripeService;

    public PaymentResponseDTO processPayment(PaymentRequestDTO request) {
        try {
            return stripeService.processPayment(request);
        } catch (Exception e) {
            log.error("Payment processing failed", e);
            throw new PaymentException("Payment processing failed: " + e.getMessage());
        }
    }

    public PaymentResponseDTO processRefund(RefundRequestDTO request) {
        try {
            return stripeService.processRefund(request);
        } catch (Exception e) {
            log.error("Refund processing failed", e);
            throw new PaymentException("Refund processing failed: " + e.getMessage());
        }
    }

    public DisputeResponseDTO handleDispute(DisputeRequestDTO request) {
        try {
            return stripeService.handleDispute(request);
        } catch (Exception e) {
            log.error("Dispute handling failed", e);
            throw new PaymentException("Dispute handling failed: " + e.getMessage());
        }
    }
} 