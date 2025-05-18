package com.demoApp.payment.service;

import com.demoApp.payment.dto.PaymentResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service responsible for handling payment-related notifications.
 * Integrates with external notification services to send payment status updates.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    @Value("${notification.service.url:http://notification-service}")
    private String notificationServiceUrl;
    
    @Value("${notification.service.enabled:false}")
    private boolean notificationsEnabled;
    
    // Uncomment when implementing actual notification service integration
    // private final RestTemplate restTemplate;
    
    /**
     * Sends a payment confirmation notification to the customer
     * @param payment The payment details
     */
    public void sendPaymentNotification(PaymentResponseDTO payment) {
        if (payment == null) {
            log.warn("Cannot send payment notification: payment is null");
            return;
        }
        
        log.info("Preparing payment notification for payment: {}", payment.getId());
        
        try {
            String title = "Payment Successful";
            String message = String.format("Your payment of %s %s has been processed successfully. " +
                            "Transaction ID: %s", 
                    payment.getAmount(), 
                    payment.getCurrency(),
                    payment.getTransactionId());
            
            sendNotification(
                    payment.getCustomerId(),
                    title,
                    message,
                    "PAYMENT_CONFIRMATION",
                    buildNotificationData(payment)
            );
            
            log.info("Payment notification sent for payment: {}", payment.getId());
            
        } catch (Exception e) {
            log.error("Failed to send payment notification for payment {}: {}", 
                    payment.getId(), e.getMessage(), e);
        }
    }

    /**
     * Sends a refund notification to the customer
     * @param payment The payment details including refund information
     */
    public void sendRefundNotification(PaymentResponseDTO payment) {
        if (payment == null || payment.getRefundedAt() == null) {
            log.warn("Cannot send refund notification: payment is null or not refunded");
            return;
        }
        
        log.info("Preparing refund notification for payment: {}", payment.getId());
        
        try {
            String title = "Refund Processed";
            String message = String.format("A refund of %s %s has been processed for your payment. " +
                            "Transaction ID: %s",
                    payment.getAmount(), 
                    payment.getCurrency(),
                    payment.getTransactionId());
            
            sendNotification(
                    payment.getCustomerId(),
                    title,
                    message,
                    "REFUND_PROCESSED",
                    buildNotificationData(payment)
            );
            
            log.info("Refund notification sent for payment: {}", payment.getId());
            
        } catch (Exception e) {
            log.error("Failed to send refund notification for payment {}: {}", 
                    payment.getId(), e.getMessage(), e);
        }
    }

    /**
     * Sends a dispute notification for the given payment
     * @param payment The payment details including dispute information
     */
    public void sendDisputeNotification(PaymentResponseDTO payment) {
        if (payment == null || payment.getDisputeId() == null) {
            log.warn("Cannot send dispute notification: payment is null or has no dispute");
            return;
        }
        
        log.info("Preparing dispute notification for payment: {}", payment.getId());
        
        try {
            String title = "Payment Dispute Opened";
            String message = String.format("A dispute has been opened for your payment of %s %s. " +
                            "Dispute ID: %s. We will review this matter and contact you soon.",
                    payment.getAmount(), 
                    payment.getCurrency(),
                    payment.getDisputeId());
            
            sendNotification(
                    payment.getCustomerId(),
                    title,
                    message,
                    "PAYMENT_DISPUTE_OPENED",
                    buildNotificationData(payment)
            );
            
            log.info("Dispute notification sent for payment: {}", payment.getId());
            
        } catch (Exception e) {
            log.error("Failed to send dispute notification for payment {}: {}", 
                    payment.getId(), e.getMessage(), e);
        }
    }
    
    /**
     * Helper method to send notification to the notification service
     */
    private void sendNotification(String recipientId, String title, String message, 
                                String notificationType, Map<String, Object> data) {
        if (!notificationsEnabled) {
            log.debug("Notifications are disabled. Not sending {} notification to {}", 
                    notificationType, recipientId);
            return;
        }
        
        try {
            // In a real implementation, this would call the notification service
            // For example:
            /*
            NotificationRequest request = new NotificationRequest(
                recipientId,
                title,
                message,
                notificationType,
                data
            );
            
            restTemplate.postForEntity(
                notificationServiceUrl + "/api/notifications",
                request,
                Void.class
            );
            */
            
            log.debug("Sent {} notification to {}: {}", notificationType, recipientId, message);
            
        } catch (Exception e) {
            log.error("Failed to send {} notification to {}: {}", 
                    notificationType, recipientId, e.getMessage(), e);
            // In a production environment, you might want to retry or use a dead letter queue
        }
    }
    
    /**
     * Builds a map of notification data from payment details
     */
    private Map<String, Object> buildNotificationData(PaymentResponseDTO payment) {
        Map<String, Object> data = new HashMap<>();
        data.put("paymentId", payment.getId());
        data.put("amount", payment.getAmount());
        data.put("currency", payment.getCurrency());
        data.put("transactionId", payment.getTransactionId());
        data.put("status", payment.getStatus().name());
        
        if (payment.getOrderId() != null) {
            data.put("orderId", payment.getOrderId());
        }
        
        if (payment.getDisputeId() != null) {
            data.put("disputeId", payment.getDisputeId());
        }
        
        if (payment.getRefundedAt() != null) {
            data.put("refundedAt", payment.getRefundedAt().toString());
        }
        
        return data;
    }
}