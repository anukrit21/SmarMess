package com.demoApp.mess.controller;

import com.demoApp.mess.dto.MessOrderDTO;
import com.demoApp.mess.entity.MessOrder;
import com.demoApp.mess.service.MessOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class MessOrderController {

    private final MessOrderService messOrderService;

    @GetMapping
    public ResponseEntity<List<MessOrder>> getAllOrders() {
        return ResponseEntity.ok(messOrderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessOrder> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(messOrderService.getOrderById(id));
    }

    @GetMapping("/mess/{messId}")
    public ResponseEntity<List<MessOrder>> getOrdersByMessId(@PathVariable Long messId) {
        return ResponseEntity.ok(messOrderService.getOrdersByMessId(messId));
    }

    @GetMapping("/status/{messId}")
    public ResponseEntity<List<MessOrder>> getOrdersByStatus(
            @PathVariable Long messId,
            @RequestParam String status) {
        return ResponseEntity.ok(messOrderService.getOrdersByStatus(
                messId, MessOrder.OrderStatus.valueOf(status.toUpperCase())));
    }

    @PostMapping
    public ResponseEntity<MessOrder> createOrder(@RequestBody MessOrderDTO orderDTO) {
        return ResponseEntity.ok(messOrderService.createOrder(orderDTO));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<MessOrder> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(messOrderService.updateOrderStatus(
                id, MessOrder.OrderStatus.valueOf(status.toUpperCase())));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        messOrderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        messOrderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/period/{messId}")
    public ResponseEntity<List<MessOrder>> getOrdersForPeriod(
            @PathVariable Long messId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return ResponseEntity.ok(messOrderService.getOrdersForPeriod(messId, start, end));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MessOrder>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(messOrderService.getUserOrders(userId));
    }

    @GetMapping("/user/{userId}/mess/{messId}")
    public ResponseEntity<List<MessOrder>> getUserOrdersForMess(
            @PathVariable Long userId,
            @PathVariable Long messId) {
        return ResponseEntity.ok(messOrderService.getUserOrdersForMess(userId, messId));
    }
}
