package com.demoApp.order.controller;

import com.demoApp.order.config.TestConfig;
import com.demoApp.order.dto.OrderDTO;
import com.demoApp.order.dto.OrderAnalyticsDTO;
import com.demoApp.order.enums.OrderStatus;
import com.demoApp.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
@WithMockUser
@ActiveProfiles("test")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    private OrderDTO orderDTO;

    @BeforeEach
    void setUp() {
        orderDTO = OrderDTO.builder()
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
    void createOrder_Success() throws Exception {
        when(orderService.createOrder(any(OrderDTO.class))).thenReturn(orderDTO);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderDTO.getId()))
                .andExpect(jsonPath("$.userId").value(orderDTO.getUserId()))
                .andExpect(jsonPath("$.messId").value(orderDTO.getMessId()))
                .andExpect(jsonPath("$.menuItemIds").isArray())
                .andExpect(jsonPath("$.totalAmount").value(orderDTO.getTotalAmount()))
                .andExpect(jsonPath("$.status").value(orderDTO.getStatus().toString()))
                .andExpect(jsonPath("$.orderTime").exists());
    }

    @Test
    void getOrder_Success() throws Exception {
        when(orderService.getOrder(1L)).thenReturn(orderDTO);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderDTO.getId()))
                .andExpect(jsonPath("$.userId").value(orderDTO.getUserId()))
                .andExpect(jsonPath("$.messId").value(orderDTO.getMessId()))
                .andExpect(jsonPath("$.menuItemIds").isArray())
                .andExpect(jsonPath("$.totalAmount").value(orderDTO.getTotalAmount()))
                .andExpect(jsonPath("$.status").value(orderDTO.getStatus().toString()))
                .andExpect(jsonPath("$.orderTime").exists());
    }

    @Test
    void getUserOrders_Success() throws Exception {
        List<OrderDTO> orders = Arrays.asList(orderDTO);
        when(orderService.getUserOrders(1L)).thenReturn(orders);

        mockMvc.perform(get("/api/orders/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(orderDTO.getId()))
                .andExpect(jsonPath("$[0].userId").value(orderDTO.getUserId()))
                .andExpect(jsonPath("$[0].messId").value(orderDTO.getMessId()))
                .andExpect(jsonPath("$[0].menuItemIds").isArray())
                .andExpect(jsonPath("$[0].totalAmount").value(orderDTO.getTotalAmount()))
                .andExpect(jsonPath("$[0].status").value(orderDTO.getStatus().toString()))
                .andExpect(jsonPath("$[0].orderTime").exists());
    }

    @Test
    void getMessOrders_Success() throws Exception {
        List<OrderDTO> orders = Arrays.asList(orderDTO);
        when(orderService.getMessOrders(1L)).thenReturn(orders);

        mockMvc.perform(get("/api/orders/mess/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(orderDTO.getId()))
                .andExpect(jsonPath("$[0].userId").value(orderDTO.getUserId()))
                .andExpect(jsonPath("$[0].messId").value(orderDTO.getMessId()))
                .andExpect(jsonPath("$[0].menuItemIds").isArray())
                .andExpect(jsonPath("$[0].totalAmount").value(orderDTO.getTotalAmount()))
                .andExpect(jsonPath("$[0].status").value(orderDTO.getStatus().toString()))
                .andExpect(jsonPath("$[0].orderTime").exists());
    }

    @Test
    void updateOrderStatus_Success() throws Exception {
        when(orderService.updateOrderStatus(1L, OrderStatus.CONFIRMED)).thenReturn(orderDTO);

        mockMvc.perform(put("/api/orders/1/status")
                .param("status", OrderStatus.CONFIRMED.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderDTO.getId()))
                .andExpect(jsonPath("$.userId").value(orderDTO.getUserId()))
                .andExpect(jsonPath("$.messId").value(orderDTO.getMessId()))
                .andExpect(jsonPath("$.menuItemIds").isArray())
                .andExpect(jsonPath("$.totalAmount").value(orderDTO.getTotalAmount()))
                .andExpect(jsonPath("$.status").value(orderDTO.getStatus().toString()))
                .andExpect(jsonPath("$.orderTime").exists());
    }

    @Test
    void assignDeliveryPerson_Success() throws Exception {
        when(orderService.assignDeliveryPerson(1L, "delivery1")).thenReturn(orderDTO);

        mockMvc.perform(put("/api/orders/1/delivery")
                .param("deliveryPersonId", "delivery1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderDTO.getId()))
                .andExpect(jsonPath("$.userId").value(orderDTO.getUserId()))
                .andExpect(jsonPath("$.messId").value(orderDTO.getMessId()))
                .andExpect(jsonPath("$.menuItemIds").isArray())
                .andExpect(jsonPath("$.totalAmount").value(orderDTO.getTotalAmount()))
                .andExpect(jsonPath("$.status").value(orderDTO.getStatus().toString()))
                .andExpect(jsonPath("$.orderTime").exists());
    }

    @Test
    void updatePaymentStatus_Success() throws Exception {
        when(orderService.updatePaymentStatus(1L, "payment1", "PAID")).thenReturn(orderDTO);

        mockMvc.perform(put("/api/orders/1/payment")
                .param("paymentId", "payment1")
                .param("status", "PAID"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderDTO.getId()))
                .andExpect(jsonPath("$.userId").value(orderDTO.getUserId()))
                .andExpect(jsonPath("$.messId").value(orderDTO.getMessId()))
                .andExpect(jsonPath("$.menuItemIds").isArray())
                .andExpect(jsonPath("$.totalAmount").value(orderDTO.getTotalAmount()))
                .andExpect(jsonPath("$.status").value(orderDTO.getStatus().toString()))
                .andExpect(jsonPath("$.orderTime").exists());
    }

    @Test
    void getOrderAnalytics_Success() throws Exception {
        OrderAnalyticsDTO analyticsDTO = OrderAnalyticsDTO.builder()
                .totalOrders(1L)
                .totalRevenue(new BigDecimal("100.00"))
                .averageOrderValue(new BigDecimal("100.00"))
                .build();

        when(orderService.getOrderAnalytics(any(), any(), any())).thenReturn(analyticsDTO);

        mockMvc.perform(get("/api/orders/analytics")
                .param("messId", "1")
                .param("startTime", LocalDateTime.now().minusDays(1).toString())
                .param("endTime", LocalDateTime.now().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalOrders").value(analyticsDTO.getTotalOrders()))
                .andExpect(jsonPath("$.totalRevenue").value(analyticsDTO.getTotalRevenue()))
                .andExpect(jsonPath("$.averageOrderValue").value(analyticsDTO.getAverageOrderValue()));
    }
} 