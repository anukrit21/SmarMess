package com.demoApp.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisputeRequestDTO {
    private Long paymentId;
    private String disputeId;
    public Long getPaymentId() {
        return paymentId;
    }
    public String getDisputeId() {
        return disputeId;
    }
    private String paymentIntentId;
    private String reason;
    private String description;
    private String evidence;
    private String customerId;
    private String orderId;

    public String getPaymentIntentId() { return paymentIntentId; }
    public String getReason() { return reason; }
    public String getDescription() { return description; }
    public String getEvidence() { return evidence; }
    public String getCustomerId() { return customerId; }
    public String getOrderId() { return orderId; }
} 