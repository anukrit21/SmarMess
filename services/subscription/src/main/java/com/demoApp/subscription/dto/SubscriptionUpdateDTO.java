package com.demoApp.subscription.dto;

import com.demoApp.subscription.enums.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionUpdateDTO {
    private SubscriptionStatus status;
    private LocalDateTime endDate;
    private LocalDateTime cancelledAt;
    private Long planId;
}
