package com.demoApp.delivery.dto;

import com.demoApp.delivery.entity.Delivery;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryTrackingDTO {
    private Long deliveryId;
    private Delivery.DeliveryStatus status;
    private Double currentLatitude;
    private Double currentLongitude;
    private LocalDateTime locationUpdatedAt;
    private LocationDTO currentLocation;
    private LocationDTO pickupLocation;
    private LocationDTO deliveryLocation;
    private LocalDateTime estimatedDeliveryTime;
    private DeliveryPersonDTO deliveryPerson;
    private List<DeliveryStatusHistory> statusHistory;
}
