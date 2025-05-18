package com.demoApp.order.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column(name = "mess_id")
    private Long messId;
    
    private boolean available;
    
    @Column(name = "category")
    private String category;
} 