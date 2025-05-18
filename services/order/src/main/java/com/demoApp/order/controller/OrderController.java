package com.demoApp.order.controller;

import com.demoApp.order.dto.*;
import com.demoApp.order.enums.OrderStatus;
import com.demoApp.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        log.info("Received request to create order for user: {}", orderDTO.getUserId());
        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long orderId) {
        log.info("Received request to get order: {}", orderId);
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getUserOrders(@PathVariable Long userId) {
        log.info("Received request to get orders for user: {}", userId);
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }

    @GetMapping("/mess/{messId}")
    public ResponseEntity<List<OrderDTO>> getMessOrders(@PathVariable Long messId) {
        log.info("Received request to get orders for mess: {}", messId);
        return ResponseEntity.ok(orderService.getMessOrders(messId));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {
        log.info("Received request to update order status to {} for order: {}", status, orderId);
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }

    @PutMapping("/{orderId}/delivery")
    public ResponseEntity<OrderDTO> assignDeliveryPerson(
            @PathVariable Long orderId,
            @RequestParam String deliveryPersonId) {
        log.info("Received request to assign delivery person {} to order: {}", deliveryPersonId, orderId);
        return ResponseEntity.ok(orderService.assignDeliveryPerson(orderId, deliveryPersonId));
    }

    @PutMapping("/{orderId}/payment")
    public ResponseEntity<OrderDTO> updatePaymentStatus(
            @PathVariable Long orderId,
            @RequestParam String paymentId,
            @RequestParam String status) {
        log.info("Received request to update payment status to {} for order: {}", status, orderId);
        return ResponseEntity.ok(orderService.updatePaymentStatus(orderId, paymentId, status));
    }

    @GetMapping("/analytics")
    public ResponseEntity<OrderAnalyticsDTO> getOrderAnalytics(
            @RequestParam String messId,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {
        log.info("Received request to get order analytics for mess: {} between {} and {}", messId, startTime, endTime);
        return ResponseEntity.ok(orderService.getOrderAnalytics(messId, startTime, endTime));
    }
} 