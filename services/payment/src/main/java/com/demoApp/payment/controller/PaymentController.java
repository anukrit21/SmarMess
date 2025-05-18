package com.demoApp.payment.controller;

import com.demoApp.payment.dto.PaymentRequestDTO;
import com.demoApp.payment.dto.PaymentResponseDTO;
import com.demoApp.payment.dto.DisputeRequestDTO;
import com.demoApp.payment.dto.RefundRequestDTO;
import com.demoApp.payment.dto.DisputeResponseDTO;
import com.demoApp.payment.dto.PaymentAnalyticsDTO;
import com.demoApp.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for payment operations
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Process a payment
     */
    @PostMapping
    public ResponseEntity<PaymentResponseDTO> processPayment(@RequestBody PaymentRequestDTO request) {
        log.info("Processing payment request for order: {}", request.getOrderId());
        return ResponseEntity.ok(paymentService.processPayment(request));
    }

    /**
     * Get payment details
     */
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> getPayment(@PathVariable Long paymentId) {
        log.info("Fetching payment details for payment ID: {}", paymentId);
        return ResponseEntity.ok(paymentService.getPaymentDetails(paymentId));
    }

    /**
     * Process a refund
     */
    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<PaymentResponseDTO> refundPayment(@PathVariable Long paymentId, @RequestBody(required = false) RefundRequestDTO refundRequestDTO) {
        log.info("Processing refund for payment ID: {}", paymentId);
        if (refundRequestDTO == null) {
            refundRequestDTO = new RefundRequestDTO();
        }
        refundRequestDTO.setPaymentId(paymentId);
        return ResponseEntity.ok(paymentService.processRefund(refundRequestDTO));
    }

    /**
     * Create a dispute
     */
    @PostMapping("/{paymentId}/dispute")
    public ResponseEntity<DisputeResponseDTO> handleDispute(
            @PathVariable String paymentId,
            @RequestParam String reason) {
        log.info("Handling dispute for payment: {}", paymentId);
        DisputeRequestDTO disputeRequest = new DisputeRequestDTO();
        disputeRequest.setPaymentId(Long.parseLong(paymentId));
        disputeRequest.setDisputeId(reason); // Assuming 'reason' is mapped to disputeId or update accordingly
        DisputeResponseDTO response = paymentService.handleDispute(paymentId, disputeRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Get payment analytics
     */
    @GetMapping("/analytics")
    public ResponseEntity<PaymentAnalyticsDTO> getPaymentAnalytics() {
        log.info("Received request for payment analytics");
        return ResponseEntity.ok(paymentService.getPaymentAnalytics());
    }
} 