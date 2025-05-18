package com.demoApp.owner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.demoApp.owner.entity.MenuItem.MenuType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemDTO {

    private Long id;

    @NotNull(message = "Owner ID is required")
    private Long ownerId;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    private boolean available = true; // Default to true if not provided

    @NotNull(message = "Menu type is required")
    private MenuType menuType;

    private String category;
}
