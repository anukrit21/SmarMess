package com.demoApp.mess.service;

import com.demoApp.mess.entity.Delivery;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
    
    private final EmailService emailService;

    @Override
    public void sendDeliveryStatusNotification(Delivery delivery) {
        log.info("Sending delivery status notification for delivery: {}", delivery.getId());
        // Use generic welcome email method until specific delivery email methods are implemented
        if (delivery.getCustomer() != null) {
            emailService.sendWelcomeEmail(
                delivery.getCustomer().getEmail(),
                delivery.getCustomer().getFirstName() + " " + delivery.getCustomer().getLastName()
            );
        }
    }

    @Override
    public void sendDeliveryAssignmentNotification(Delivery delivery) {
        log.info("Sending delivery assignment notification for delivery: {}", delivery.getId());
        // Use generic email method since specific delivery email methods are not implemented yet
        // Implementation will be added when the specific methods are available
    }

    @Override
    public void sendDeliveryCancellationNotification(Delivery delivery) {
        log.info("Sending delivery cancellation notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryIssueNotification(Delivery delivery) {
        log.info("Sending delivery issue notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryResolutionNotification(Delivery delivery) {
        log.info("Sending delivery resolution notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryRatingNotification(Delivery delivery) {
        log.info("Sending delivery rating notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryPersonAssignmentNotification(Delivery delivery) {
        log.info("Sending delivery person assignment notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryPersonStatusUpdateNotification(Delivery delivery) {
        log.info("Sending delivery person status update notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryPersonLocationUpdateNotification(Delivery delivery) {
        log.info("Sending delivery person location update notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryPersonRatingUpdateNotification(Delivery delivery) {
        log.info("Sending delivery person rating update notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryPersonIssueNotification(Delivery delivery) {
        log.info("Sending delivery person issue notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryPersonResolutionNotification(Delivery delivery) {
        log.info("Sending delivery person resolution notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryPersonCancellationNotification(Delivery delivery) {
        log.info("Sending delivery person cancellation notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryPersonAssignmentCancellationNotification(Delivery delivery) {
        log.info("Sending delivery person assignment cancellation notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryPersonAssignmentRejectionNotification(Delivery delivery) {
        log.info("Sending delivery person assignment rejection notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryPersonAssignmentAcceptanceNotification(Delivery delivery) {
        log.info("Sending delivery person assignment acceptance notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryPersonAssignmentCompletionNotification(Delivery delivery) {
        log.info("Sending delivery person assignment completion notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryPersonAssignmentFailureNotification(Delivery delivery) {
        log.info("Sending delivery person assignment failure notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryPersonAssignmentSuccessNotification(Delivery delivery) {
        log.info("Sending delivery person assignment success notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryPersonAssignmentTimeoutNotification(Delivery delivery) {
        log.info("Sending delivery person assignment timeout notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryPersonAssignmentReminderNotification(Delivery delivery) {
        log.info("Sending delivery person assignment reminder notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryPersonAssignmentUpdateNotification(Delivery delivery) {
        log.info("Sending delivery person assignment update notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryPersonAssignmentConfirmationNotification(Delivery delivery) {
        log.info("Sending delivery person assignment confirmation notification for delivery: {}", delivery.getId());
        // Implementation
    }

    @Override
    public void sendDeliveryPersonAssignmentReassignmentNotification(Delivery delivery) {
        log.info("Sending delivery person assignment reassignment notification for delivery: {}", delivery.getId());
        // Implementation
    }
} 