package com.demoApp.order.service;

import com.demoApp.order.dto.OrderDTO;
import com.demoApp.order.dto.OrderAnalyticsDTO;
import com.demoApp.order.entity.Order;
import com.demoApp.order.enums.OrderStatus;
import com.demoApp.order.exception.ResourceNotFoundException;
import com.demoApp.order.repository.MenuRepository;
import com.demoApp.order.repository.MessRepository;
import com.demoApp.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MessRepository messRepository;

    @InjectMocks
    private OrderService orderService;

    private OrderDTO orderDTO;
    private Order order;

    @BeforeEach
    void setUp() {
        orderDTO = OrderDTO.builder()
                .userId(1L)
                .messId(1L)
                .menuItemIds(Arrays.asList(1L, 2L))
                .totalAmount(new BigDecimal("100.00"))
                .build();

        order = Order.builder()
                .id(1L)
                .userId(1L)
                .messId(1L)
                .menuItemIds(Arrays.asList(1L, 2L))
                .totalAmount(new BigDecimal("100.00"))
                .status(OrderStatus.PENDING)
                .orderTime(LocalDateTime.now())
                .build();
    }

    @Test
    void createOrder_Success() {
        when(messRepository.existsById(any())).thenReturn(true);
        when(menuRepository.existsById(any())).thenReturn(true);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO result = orderService.createOrder(orderDTO);

        assertNotNull(result);
        assertEquals(orderDTO.getUserId(), result.getUserId());
        assertEquals(orderDTO.getMessId(), result.getMessId());
        assertEquals(orderDTO.getMenuItemIds(), result.getMenuItemIds());
        assertEquals(orderDTO.getTotalAmount(), result.getTotalAmount());
        assertEquals(OrderStatus.PENDING, result.getStatus());
        assertNotNull(result.getOrderTime());

        verify(messRepository).existsById(any());
        verify(menuRepository, times(2)).existsById(any());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void createOrder_MessNotFound() {
        when(messRepository.existsById(any())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(orderDTO));

        verify(messRepository).existsById(any());
        verify(menuRepository, never()).existsById(any());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void createOrder_MenuItemNotFound() {
        when(messRepository.existsById(any())).thenReturn(true);
        when(menuRepository.existsById(any())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(orderDTO));

        verify(messRepository).existsById(any());
        verify(menuRepository).existsById(any());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void getOrder_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderDTO result = orderService.getOrder(1L);

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getUserId(), result.getUserId());
        assertEquals(order.getMessId(), result.getMessId());
        assertEquals(order.getMenuItemIds(), result.getMenuItemIds());
        assertEquals(order.getTotalAmount(), result.getTotalAmount());
        assertEquals(order.getStatus(), result.getStatus());
        assertEquals(order.getOrderTime(), result.getOrderTime());

        verify(orderRepository).findById(1L);
    }

    @Test
    void getOrder_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrder(1L));

        verify(orderRepository).findById(1L);
    }

    @Test
    void getUserOrders_Success() {
        when(orderRepository.findByUserId(1L)).thenReturn(Arrays.asList(order));

        List<OrderDTO> result = orderService.getUserOrders(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(order.getId(), result.get(0).getId());
        assertEquals(order.getUserId(), result.get(0).getUserId());
        assertEquals(order.getMessId(), result.get(0).getMessId());
        assertEquals(order.getMenuItemIds(), result.get(0).getMenuItemIds());
        assertEquals(order.getTotalAmount(), result.get(0).getTotalAmount());
        assertEquals(order.getStatus(), result.get(0).getStatus());
        assertEquals(order.getOrderTime(), result.get(0).getOrderTime());

        verify(orderRepository).findByUserId(1L);
    }

    @Test
    void getMessOrders_Success() {
        when(orderRepository.findByMessId(1L)).thenReturn(Arrays.asList(order));

        List<OrderDTO> result = orderService.getMessOrders(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(order.getId(), result.get(0).getId());
        assertEquals(order.getUserId(), result.get(0).getUserId());
        assertEquals(order.getMessId(), result.get(0).getMessId());
        assertEquals(order.getMenuItemIds(), result.get(0).getMenuItemIds());
        assertEquals(order.getTotalAmount(), result.get(0).getTotalAmount());
        assertEquals(order.getStatus(), result.get(0).getStatus());
        assertEquals(order.getOrderTime(), result.get(0).getOrderTime());

        verify(orderRepository).findByMessId(1L);
    }

    @Test
    void updateOrderStatus_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO result = orderService.updateOrderStatus(1L, OrderStatus.CONFIRMED);

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getUserId(), result.getUserId());
        assertEquals(order.getMessId(), result.getMessId());
        assertEquals(order.getMenuItemIds(), result.getMenuItemIds());
        assertEquals(order.getTotalAmount(), result.getTotalAmount());
        assertEquals(OrderStatus.CONFIRMED, result.getStatus());
        assertEquals(order.getOrderTime(), result.getOrderTime());

        verify(orderRepository).findById(1L);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void updateOrderStatus_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.updateOrderStatus(1L, OrderStatus.CONFIRMED));

        verify(orderRepository).findById(1L);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void assignDeliveryPerson_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO result = orderService.assignDeliveryPerson(1L, "delivery1");

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getUserId(), result.getUserId());
        assertEquals(order.getMessId(), result.getMessId());
        assertEquals(order.getMenuItemIds(), result.getMenuItemIds());
        assertEquals(order.getTotalAmount(), result.getTotalAmount());
        assertEquals(OrderStatus.OUT_FOR_DELIVERY, result.getStatus());
        assertEquals(order.getOrderTime(), result.getOrderTime());
        assertEquals("delivery1", result.getDeliveryPersonId());

        verify(orderRepository).findById(1L);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void assignDeliveryPerson_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.assignDeliveryPerson(1L, "delivery1"));

        verify(orderRepository).findById(1L);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void updatePaymentStatus_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO result = orderService.updatePaymentStatus(1L, "payment1", "PAID");

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getUserId(), result.getUserId());
        assertEquals(order.getMessId(), result.getMessId());
        assertEquals(order.getMenuItemIds(), result.getMenuItemIds());
        assertEquals(order.getTotalAmount(), result.getTotalAmount());
        assertEquals(order.getStatus(), result.getStatus());
        assertEquals(order.getOrderTime(), result.getOrderTime());
        assertEquals("payment1", result.getPaymentId());
        assertEquals("PAID", result.getPaymentStatus());

        verify(orderRepository).findById(1L);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void updatePaymentStatus_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.updatePaymentStatus(1L, "payment1", "PAID"));

        verify(orderRepository).findById(1L);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void getOrderAnalytics_Success() {
        List<Order> orders = Arrays.asList(order);
        when(orderRepository.findByOrderTimeBetween(any(), any())).thenReturn(orders);

        OrderAnalyticsDTO result = orderService.getOrderAnalytics("1", LocalDateTime.now().minusDays(1), LocalDateTime.now());

        assertNotNull(result);
        assertEquals(1L, result.getTotalOrders());
        assertEquals(order.getTotalAmount(), result.getTotalRevenue());
        assertEquals(order.getTotalAmount(), result.getAverageOrderValue());

        verify(orderRepository).findByOrderTimeBetween(any(), any());
    }
} 