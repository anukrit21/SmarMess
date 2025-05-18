package com.demoApp.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PopularItemDTO {
    private Long menuId;
    private String menuName;
    private Integer orderCount;
    private Double totalRevenue;
} 