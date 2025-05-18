package com.demoApp.payment.service;

import com.demoApp.payment.dto.*;
import com.demoApp.payment.entity.Payment;
import com.demoApp.payment.entity.PaymentMethodType;
import com.demoApp.payment.entity.PaymentStatus;
import com.demoApp.payment.repository.PaymentRepository;
import com.demoApp.payment.repository.PaymentAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.demoApp.payment.exception.PaymentException;
import com.demoApp.payment.exception.ResourceNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentAnalyticsRepository analyticsRepository;
    private final PaymentGatewayService paymentGatewayService;

    @Transactional
    public PaymentResponseDTO processPayment(PaymentRequestDTO request) {
        log.info("Processing payment for order: {}", request.getOrderId());
        
        Payment payment = Payment.builder()
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .paymentMethod(request.getPaymentMethod())
                .status(PaymentStatus.PENDING)
                .orderId(request.getOrderId())
                .description(request.getDescription())
                .build();

        try {
            PaymentResponseDTO gatewayResponse = paymentGatewayService.processPayment(request);
            payment.setPaymentIntentId(gatewayResponse.getPaymentIntentId());
            payment.setStatus(PaymentStatus.SUCCEEDED);
            payment.setCustomerId(gatewayResponse.getCustomerId());
            
            Payment savedPayment = paymentRepository.save(payment);
            return convertToDTO(savedPayment);
        } catch (Exception e) {
            payment.setStatus(PaymentStatus.FAILED);
            payment.setErrorMessage(e.getMessage());
            paymentRepository.save(payment);
            throw new PaymentException("Payment processing failed: " + e.getMessage());
        }
    }

    @Transactional
    public PaymentResponseDTO processRefund(String paymentId, RefundRequestDTO refundRequest) {
        refundRequest.setPaymentId(Long.parseLong(paymentId));
        return processRefund(refundRequest);
    }

    @Transactional
    public PaymentResponseDTO processRefund(RefundRequestDTO refundRequestDTO) {
        log.info("Processing refund for payment: {}", refundRequestDTO.getPaymentId());
        
        Payment payment = paymentRepository.findById(refundRequestDTO.getPaymentId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + refundRequestDTO.getPaymentId()));

        if (payment.getStatus() != PaymentStatus.SUCCEEDED) {
            throw new PaymentException("Cannot refund payment that is not successful");
        }

        try {
            paymentGatewayService.processRefund(refundRequestDTO);
            payment.setStatus(PaymentStatus.REFUNDED);
            payment.setRefundedAt(LocalDateTime.now());
            
            Payment savedPayment = paymentRepository.save(payment);
            return convertToDTO(savedPayment);
        } catch (Exception e) {
            throw new PaymentException("Refund processing failed: " + e.getMessage());
        }
    }

    @Transactional
    public DisputeResponseDTO handleDispute(String paymentId, DisputeRequestDTO request) {
        log.info("Handling dispute for payment: {}", paymentId);
        
        Payment payment = paymentRepository.findById(Long.parseLong(paymentId))
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + paymentId));

        try {
            DisputeResponseDTO disputeResponse = paymentGatewayService.handleDispute(request);
            payment.setStatus(PaymentStatus.DISPUTED);
            payment.setDisputeId(disputeResponse.getDisputeId());
            payment.setDisputedAt(LocalDateTime.now());
            
            Payment savedPayment = paymentRepository.save(payment);
            return convertToDisputeResponseDTO(savedPayment);
        } catch (Exception e) {
            throw new PaymentException("Dispute handling failed: " + e.getMessage());
        }
    }

    /**
     * Processes payment based on the payment method.
     * @param payment The payment to process
     */
    @SuppressWarnings("unused")
    private void processPaymentByMethod(Payment payment) {
        PaymentMethodType type = payment.getPaymentMethod();
        switch (type) {
            case CREDIT_CARD:
            case DEBIT_CARD:
                processCardPayment(payment);
                break;
            case BANK_TRANSFER:
                processBankTransfer(payment);
                break;
            case PAYPAL:
                processPayPalPayment(payment);
                break;
            case WALLET:
                processWalletPayment(payment);
                break;
            case UPI:
                processUPIPayment(payment);
                break;
            case CASH_ON_DELIVERY:
                processCashOnDelivery(payment);
                break;
            default:
                throw new PaymentException("Unsupported payment method: " + payment.getPaymentMethod());
        }
    }

    /**
     * Processes a UPI payment transaction.
     * Validates the payment details and initiates the UPI transaction.
     *
     * @param payment The payment to process
     * @throws PaymentException if the payment processing fails
     */
    private void processUPIPayment(Payment payment) {
        log.info("Processing UPI payment for order: {}", payment.getOrderId());
        
        // Set initial processing timestamp
        payment.setProcessedAt(LocalDateTime.now());
        
        try {
            // Validate payment details
            validateUPIPayment(payment);
            
            // Simulate UPI transaction processing
            log.debug("Initiating UPI transaction for payment ID: {}", payment.getId());
            
            // In a real implementation, this would integrate with a UPI payment gateway
            // For now, we'll simulate a successful transaction after a short delay
            simulateUPITransaction(payment);
            
            // Update payment status based on transaction result
            payment.markAsCompleted();
            payment.setTransactionId("UPI" + System.currentTimeMillis());
            payment.setStatus(PaymentStatus.SUCCEEDED);
            
            log.info("UPI payment processed successfully for order: {}", payment.getOrderId());
            
        } catch (Exception e) {
            log.error("UPI payment processing failed for order {}: {}", 
                    payment.getOrderId(), e.getMessage(), e);
            payment.setStatus(PaymentStatus.FAILED);
            payment.setErrorMessage(e.getMessage());
            throw new PaymentException("UPI payment failed: " + e.getMessage(), e);
        } finally {
            payment.setProcessedAt(LocalDateTime.now());
            paymentRepository.save(payment);
        }
    }
    
    /**
     * Validates UPI payment details.
     * 
     * @param payment The payment to validate
     * @throws PaymentException if validation fails
     */
    private void validateUPIPayment(Payment payment) {
        if (payment.getAmount() == null || payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentException("Invalid payment amount");
        }
        
        // Get UPI ID from metadata
        String upiId = payment.getMetadataMap() != null ? payment.getMetadataMap().get("upiId") : null;
        
        if (upiId == null || upiId.trim().isEmpty()) {
            throw new PaymentException("UPI ID is required in payment metadata");
        }
        
        // Basic UPI ID format validation (simplified)
        if (!upiId.matches(".+@.+")) {
            throw new PaymentException("Invalid UPI ID format. Must be in format: user@vpa");
        }
        
        // Additional validation can be added here (e.g., amount limits, etc.)
    }
    
    /**
     * Simulates a UPI transaction with a delay.
     * In a real implementation, this would call a UPI payment gateway.
     */
    private void simulateUPITransaction(Payment payment) {
        try {
            // Simulate network delay (1-3 seconds)
            long delay = 1000 + (long) (Math.random() * 2000);
            Thread.sleep(delay);
            
            // Simulate occasional failures (5% failure rate for testing)
            if (Math.random() < 0.05) { // 5% chance of failure
                throw new PaymentException("UPI transaction failed: Insufficient funds");
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PaymentException("UPI transaction was interrupted", e);
        }
    }

    private void processCardPayment(Payment payment) {
        payment.markAsCompleted();
    }

    private void processBankTransfer(Payment payment) {
        payment.setStatus(PaymentStatus.PROCESSING);
    }

    private void processPayPalPayment(Payment payment) {
        payment.markAsCompleted();
    }

    private void processWalletPayment(Payment payment) {
        payment.markAsCompleted();
    }

    private void processCashOnDelivery(Payment payment) {
        payment.setStatus(PaymentStatus.PENDING);
    }

    /**
     * Generates a unique transaction ID.
     * @return A string representing a unique transaction ID
     */
    @SuppressWarnings("unused")
    private String generateTransactionId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public PaymentAnalyticsDTO getPaymentAnalytics() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(30); // Default to last 30 days
        return getPaymentAnalytics(startDate, endDate);
    }

    public PaymentAnalyticsDTO getPaymentAnalytics(LocalDateTime startDate, LocalDateTime endDate) {
        Double totalAmount = paymentRepository.sumAmountByCreatedAtBetween(startDate, endDate);
        Double avgAmount = paymentRepository.avgAmountByCreatedAtBetween(startDate, endDate);
        return PaymentAnalyticsDTO.builder()
            .totalTransactions(paymentRepository.countByCreatedAtBetween(startDate, endDate))
            .totalAmount(totalAmount != null ? java.math.BigDecimal.valueOf(totalAmount) : java.math.BigDecimal.ZERO)
            .averageTransactionAmount(avgAmount != null ? java.math.BigDecimal.valueOf(avgAmount) : java.math.BigDecimal.ZERO)
            .successfulTransactions(paymentRepository.countByStatusAndCreatedAtBetween(PaymentStatus.COMPLETED, startDate, endDate))
            .failedTransactions(paymentRepository.countByStatusAndCreatedAtBetween(PaymentStatus.FAILED, startDate, endDate))
            .refundedTransactions(paymentRepository.countByStatusAndCreatedAtBetween(PaymentStatus.REFUNDED, startDate, endDate))
            .disputedTransactions(paymentRepository.countByStatusAndCreatedAtBetween(PaymentStatus.DISPUTED, startDate, endDate))
            .build();
    }

    public PaymentResponseDTO getPaymentDetails(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new PaymentException("Payment not found with id: " + paymentId));
        return convertToDTO(payment);
    }

    public DisputeResponseDTO getDisputeDetails(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + paymentId));

        return DisputeResponseDTO.builder()
            .id(payment.getId())
            .orderId(payment.getOrderId())
            .userId(payment.getUserId())
            .amount(payment.getAmount())
            .disputeId(payment.getDisputeId())
            .status(payment.getStatus())
            .disputedAt(payment.getDisputedAt())
            .build();
    }

    public Page<PaymentResponseDTO> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public PaymentResponseDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        return convertToDTO(payment);
    }

    public PaymentResponseDTO getPaymentById(String paymentId) {
        try {
            Long id = Long.parseLong(paymentId);
            return getPaymentById(id);
        } catch (NumberFormatException e) {
            throw new PaymentException("Invalid payment ID format: " + paymentId);
        }
    }

    public BigDecimal calculateTotalProcessedAmount(LocalDateTime startDate, LocalDateTime endDate) {
        return paymentRepository.sumAmountByStatusAndDateRange(PaymentStatus.COMPLETED, startDate, endDate);
    }

    public void updatePaymentStatus(String paymentId, PaymentStatus status, String transactionId, String disputeId) {
        try {
            Long id = Long.parseLong(paymentId);
            Payment payment = paymentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Payment not found"));

            payment.setStatus(status);
            if (transactionId != null) {
                payment.setTransactionId(transactionId);
            }
            if (disputeId != null) {
                payment.setDisputeId(disputeId);
            }

            paymentRepository.save(payment);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid payment ID format: " + paymentId);
        }
    }

    /**
     * Validates the payment request.
     * @param request The payment request to validate
     * @throws PaymentException if the request is invalid
     */
    @SuppressWarnings("unused")
    private void validatePaymentRequest(PaymentRequestDTO request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentException("Payment amount must be greater than zero");
        }
        if (request.getPaymentMethod() == null) {
            throw new PaymentException("Payment method is required");
        }
    }

    /**
     * Updates payment analytics when a payment is processed.
     * @param payment The payment that was processed
     */
    @SuppressWarnings("unused")
    private void updatePaymentAnalytics(Payment payment) {
        analyticsRepository.incrementTotalPayments();
        analyticsRepository.incrementTotalAmount(payment.getAmount());
        // analyticsRepository.incrementPaymentMethodCount(payment.getPaymentMethod().name()); // Method removed due to JPQL limitation
    }

    /**
     * Updates refund analytics when a refund is processed.
     * @param payment The payment that was refunded
     */
    @SuppressWarnings("unused")
    private void updateRefundAnalytics(Payment payment) {
        analyticsRepository.incrementTotalRefunds();
        analyticsRepository.incrementTotalRefundAmount(payment.getAmount());
    }

    /**
     * Updates dispute analytics when a dispute is processed.
     * @param payment The payment that was disputed
     */
    @SuppressWarnings("unused")
    private void updateDisputeAnalytics(Payment payment) {
        analyticsRepository.incrementTotalDisputes();
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> convertMetadataToMap(Object metadata) {
        if (metadata == null) {
            return null;
        }
        if (metadata instanceof Map) {
            return (Map<String, String>) metadata;
        }
        if (metadata instanceof String) {
            String metadataStr = (String) metadata;
            if (metadataStr.isEmpty()) {
                return null;
            }
            try {
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                return mapper.readValue(metadataStr, new com.fasterxml.jackson.core.type.TypeReference<Map<String, String>>() {});
            } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                // Optionally log or handle parse error
                return null;
            }
        }
        return null;
    }

    public PaymentResponseDTO convertToDTO(Payment payment) {
        return PaymentResponseDTO.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .paymentIntentId(payment.getPaymentIntentId())
                .customerId(payment.getCustomerId())
                .orderId(payment.getOrderId())
                .description(payment.getDescription())
                .errorMessage(payment.getErrorMessage())
                .disputeId(payment.getDisputeId())
                .disputedAt(payment.getDisputedAt())
                .refundedAt(payment.getRefundedAt())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .metadata(convertMetadataToMap(payment.getMetadata()))
                .build();
    }

    public List<PaymentResponseDTO> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PaymentResponseDTO> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DisputeResponseDTO convertToDisputeResponseDTO(Payment payment) {
        return DisputeResponseDTO.builder()
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .customerId(payment.getCustomerId())
                .amount(payment.getAmount())
                .disputeId(payment.getDisputeId())
                .status(payment.getStatus())
                .disputedAt(payment.getDisputedAt())
                .build();
    }

    public List<PaymentResponseDTO> getPaymentsByOrderId(String orderId) {
        return paymentRepository.findByOrderId(Long.valueOf(orderId)).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PaymentResponseDTO> getPaymentsByCustomerId(String customerId) {
        return paymentRepository.findByCustomerId(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Map<PaymentStatus, Long> getPaymentStats() {
        return paymentRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Payment::getStatus,
                        Collectors.counting()
                ));
    }
}
