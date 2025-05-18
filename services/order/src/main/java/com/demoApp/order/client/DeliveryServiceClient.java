package com.demoApp.order.client;

import com.demoApp.order.dto.DeliveryRequestDTO;
import com.demoApp.order.dto.DeliveryAssignmentDTO;
import com.demoApp.order.dto.DeliveryStatusDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "delivery-service")
public interface DeliveryServiceClient {
    @PostMapping("/api/delivery/assign")
    DeliveryAssignmentDTO assignDelivery(@RequestBody DeliveryRequestDTO request);

    @GetMapping("/api/delivery/{orderId}/status")
    DeliveryStatusDTO getDeliveryStatus(@PathVariable("orderId") Long orderId);
} 