package com.demoApp.mess.service;

import com.demoApp.mess.entity.Delivery;

public interface NotificationService {
    // Delivery notifications
    void sendDeliveryStatusNotification(Delivery delivery);
    void sendDeliveryAssignmentNotification(Delivery delivery);
    void sendDeliveryCancellationNotification(Delivery delivery);
    void sendDeliveryIssueNotification(Delivery delivery);
    void sendDeliveryResolutionNotification(Delivery delivery);
    void sendDeliveryRatingNotification(Delivery delivery);
    
    // Delivery person notifications
    void sendDeliveryPersonAssignmentNotification(Delivery delivery);
    void sendDeliveryPersonStatusUpdateNotification(Delivery delivery);
    void sendDeliveryPersonLocationUpdateNotification(Delivery delivery);
    void sendDeliveryPersonRatingUpdateNotification(Delivery delivery);
    void sendDeliveryPersonIssueNotification(Delivery delivery);
    void sendDeliveryPersonResolutionNotification(Delivery delivery);
    void sendDeliveryPersonCancellationNotification(Delivery delivery);
    
    // Assignment notifications
    void sendDeliveryPersonAssignmentCancellationNotification(Delivery delivery);
    void sendDeliveryPersonAssignmentRejectionNotification(Delivery delivery);
    void sendDeliveryPersonAssignmentAcceptanceNotification(Delivery delivery);
    void sendDeliveryPersonAssignmentCompletionNotification(Delivery delivery);
    void sendDeliveryPersonAssignmentFailureNotification(Delivery delivery);
    void sendDeliveryPersonAssignmentSuccessNotification(Delivery delivery);
    void sendDeliveryPersonAssignmentTimeoutNotification(Delivery delivery);
    void sendDeliveryPersonAssignmentReminderNotification(Delivery delivery);
    void sendDeliveryPersonAssignmentUpdateNotification(Delivery delivery);
    void sendDeliveryPersonAssignmentConfirmationNotification(Delivery delivery);
    void sendDeliveryPersonAssignmentReassignmentNotification(Delivery delivery);
}