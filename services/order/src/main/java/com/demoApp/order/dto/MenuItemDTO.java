package com.demoApp.order.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MenuItemDTO {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Boolean isAvailable;
    private String messId;
} 