package com.demoApp.order.client;

import com.demoApp.order.dto.MenuItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "menu-service")
public interface MenuServiceClient {
    @GetMapping("/api/menu/{messId}")
    List<MenuItemDTO> getMenuItemsByMessId(@PathVariable("messId") Long messId);

    @PostMapping("/api/menu/validate")
    boolean validateMenuItems(@RequestBody List<Long> menuItemIds);
} 