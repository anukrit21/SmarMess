package com.demoApp.order.service;

import com.demoApp.order.client.*;
import com.demoApp.order.dto.*;
import com.demoApp.order.entity.*;
import com.demoApp.order.enums.OrderStatus;
import com.demoApp.order.exception.ResourceNotFoundException;
import com.demoApp.order.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final DeliveryAllocationService deliveryAllocationService;
    private final UserServiceClient userServiceClient;
    private final MenuServiceClient menuServiceClient;
    private final PaymentServiceClient paymentServiceClient;
    private final DeliveryServiceClient deliveryServiceClient;
    private final NotificationService notificationService;
    private final MenuRepository menuRepository;
    private final MessRepository messRepository;

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        log.info("Creating new order for user: {}", orderDTO.getUserId());
        
        // Validate mess exists
        if (!messRepository.existsById(orderDTO.getMessId())) {
            throw new ResourceNotFoundException("Mess not found with id: " + orderDTO.getMessId());
        }

        // Validate menu items exist and are available
        orderDTO.getMenuItemIds().forEach(menuId -> {
            if (!menuRepository.existsById(menuId)) {
                throw new ResourceNotFoundException("Menu item not found with id: " + menuId);
            }
        });

        Order order = Order.builder()
                .userId(orderDTO.getUserId())
                .messId(orderDTO.getMessId())
                .menuItemIds(orderDTO.getMenuItemIds())
                .totalAmount(orderDTO.getTotalAmount())
                .status(OrderStatus.PENDING)
                .orderTime(LocalDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderDTO getOrder(Long orderId) {
        log.info("Fetching order with id: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        return convertToDTO(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getUserOrders(Long userId) {
        log.info("Fetching orders for user: {}", userId);
        return orderRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getMessOrders(Long messId) {
        log.info("Fetching orders for mess: {}", messId);
        return orderRepository.findByMessId(messId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO updateOrderStatus(Long orderId, OrderStatus status) {
        log.info("Updating order status to {} for order: {}", status, orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        
        order.setStatus(status);
        if (status == OrderStatus.DELIVERED) {
            order.setDeliveryTime(LocalDateTime.now());
        }
        
        Order updatedOrder = orderRepository.save(order);
        return convertToDTO(updatedOrder);
    }

    @Transactional
    public OrderDTO assignDeliveryPerson(Long orderId, String deliveryPersonId) {
        log.info("Assigning delivery person {} to order: {}", deliveryPersonId, orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        
        order.setDeliveryPersonId(deliveryPersonId);
        order.setStatus(OrderStatus.OUT_FOR_DELIVERY);
        
        Order updatedOrder = orderRepository.save(order);
        return convertToDTO(updatedOrder);
    }

    @Transactional
    public OrderDTO updatePaymentStatus(Long orderId, String paymentId, String status) {
        log.info("Updating payment status to {} for order: {}", status, orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        
        order.setPaymentId(paymentId);
        order.setPaymentStatus(status);
        
        Order updatedOrder = orderRepository.save(order);
        return convertToDTO(updatedOrder);
    }

    @Transactional(readOnly = true)
    public OrderAnalyticsDTO getOrderAnalytics(String messId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("Fetching order analytics for mess: {} between {} and {}", messId, startTime, endTime);
        List<Order> orders = orderRepository.findByOrderTimeBetween(startTime, endTime);
        
        return OrderAnalyticsDTO.builder()
                .totalOrders((long) orders.size())
                .totalRevenue(orders.stream()
                        .map(Order::getTotalAmount)
                        .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add))
                .averageOrderValue(orders.isEmpty() ? java.math.BigDecimal.ZERO :
                        orders.stream()
                                .map(Order::getTotalAmount)
                                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add)
                                .divide(new java.math.BigDecimal(orders.size())))
                .build();
    }

    private void sendOrderStatusNotification(Order order) {
        String message = String.format("Order #%d status updated to %s", order.getId(), order.getStatus());
        
        // Send notification to user
        notificationService.sendNotification(
            order.getUserId(),
            "Order Status Update",
            message,
            NotificationType.ORDER_CONFIRMED
        );
        
        // Send notification to mess owner if status is DELIVERED or CANCELLED
        if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.CANCELLED) {
            Mess mess = messRepository.findById(order.getMessId())
                .orElseThrow(() -> new ResourceNotFoundException("Mess not found with id: " + order.getMessId()));
            
            notificationService.sendNotification(
                mess.getOwnerId(),
                "Order Update",
                message,
                NotificationType.ORDER_DELIVERED
            );
        }
    }

    private void updateOrderAnalytics(Order order) {
        // Update menu item popularity
        order.getMenuItemIds().forEach(menuId -> {
            Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + menuId));
            menu.setOrderCount(menu.getOrderCount() == null ? 1L : menu.getOrderCount() + 1L);
            menuRepository.save(menu);
        });
        
        // Update mess analytics
        Mess mess = messRepository.findById(order.getMessId())
            .orElseThrow(() -> new ResourceNotFoundException("Mess not found with id: " + order.getMessId()));
        mess.setTotalOrders(mess.getTotalOrders() == null ? 1L : mess.getTotalOrders() + 1L);
        mess.setTotalRevenue(mess.getTotalRevenue() == null ? order.getTotalAmount() : mess.getTotalRevenue().add(order.getTotalAmount()));
        messRepository.save(mess);
    }

    private BigDecimal calculateTotalRevenue(List<Order> orders) {
        return orders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateAverageOrderValue(List<Order> orders) {
        if (orders.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return calculateTotalRevenue(orders).divide(new BigDecimal(orders.size()), 2, RoundingMode.HALF_UP);
    }

    private List<PopularItemDTO> getPopularItems(List<Order> orders) {
        Map<Long, Long> itemCounts = orders.stream()
            .flatMap(order -> order.getMenuItemIds().stream())
            .collect(Collectors.groupingBy(
                menuId -> menuId,
                Collectors.counting()
            ));

        return itemCounts.entrySet().stream()
            .map(entry -> PopularItemDTO.builder()
                .menuId(entry.getKey())
                .orderCount(entry.getValue().intValue())
                .build())
            .sorted((a, b) -> b.getOrderCount() - a.getOrderCount())
            .limit(10)
            .collect(Collectors.toList());
    }

    private Map<OrderStatus, Integer> getOrderStatusDistribution(List<Order> orders) {
        return orders.stream()
            .collect(Collectors.groupingBy(
                Order::getStatus,
                Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
            ));
    }

    private LocalDateTime calculateExpectedDeliveryTime(LocalDateTime orderTime) {
        // Add 45 minutes for preparation and delivery
        return orderTime.plusMinutes(45);
    }

    private OrderDTO convertToDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .messId(order.getMessId())
                .menuItemIds(order.getMenuItemIds())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .orderTime(order.getOrderTime())
                .deliveryTime(order.getDeliveryTime())
                .deliveryPersonId(order.getDeliveryPersonId())
                .paymentId(order.getPaymentId())
                .paymentStatus(order.getPaymentStatus())
                .build();
    }

    private DeliveryPersonDTO convertToDeliveryPersonDTO(DeliveryPerson deliveryPerson) {
        return DeliveryPersonDTO.builder()
            .id(deliveryPerson.getId())
            .name(deliveryPerson.getName())
            .phoneNumber(deliveryPerson.getPhoneNumber())
            .currentLocation(deliveryPerson.getCurrentLocation())
            .rating(deliveryPerson.getRating())
            .build();
    }
} 