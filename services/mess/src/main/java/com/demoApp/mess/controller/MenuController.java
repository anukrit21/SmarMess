package com.demoApp.mess.controller;

import com.demoApp.mess.dto.MenuItemDTO;
import com.demoApp.mess.dto.MenuItemCreateDTO;
import com.demoApp.mess.dto.MenuItemUpdateDTO;
import com.demoApp.mess.entity.MenuItem;
import com.demoApp.mess.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/menu-items")
@CrossOrigin(origins = "*")
public class MenuController {
    private final MenuService menuService;
    
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ResponseEntity<List<MenuItemDTO>> getAllMenuItems() {
        return ResponseEntity.ok(menuService.getAllMenuItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDTO> getMenuItemById(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuItemById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + id)));
    }

    @PostMapping
    public ResponseEntity<MenuItemDTO> createMenuItem(@RequestBody MenuItemCreateDTO menuItemCreateDTO) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemCreateDTO.getName());
        menuItem.setDescription(menuItemCreateDTO.getDescription());
        menuItem.setPrice(menuItemCreateDTO.getPrice());
        menuItem.setIsVegetarian(menuItemCreateDTO.getIsVegetarian());
        menuItem.setIsAvailable(true);
        
        return ResponseEntity.ok(menuService.createMenuItem(menuItem));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemDTO> updateMenuItem(@PathVariable Long id, @RequestBody MenuItemUpdateDTO menuItemUpdateDTO) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemUpdateDTO.getName());
        menuItem.setDescription(menuItemUpdateDTO.getDescription());
        menuItem.setPrice(menuItemUpdateDTO.getPrice() != null ? 
            BigDecimal.valueOf(menuItemUpdateDTO.getPrice()) : null);
        menuItem.setIsVegetarian(menuItemUpdateDTO.getIsVegetarian());
        menuItem.setIsSpicy(menuItemUpdateDTO.getIsSpicy());
        menuItem.setIsAvailable(menuItemUpdateDTO.getIsAvailable());
        menuItem.setPreparationTime(menuItemUpdateDTO.getPreparationTime());
        
        return ResponseEntity.ok(menuService.updateMenuItem(id, menuItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuService.deleteMenuItem(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<MenuItemDTO>> searchMenuItems(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Boolean isVeg,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String category) {
        
        List<MenuItemDTO> menuItems = menuService.getAllMenuItems();
        
        // Filter by query (name or description)
        if (query != null && !query.isEmpty()) {
            String lowerQuery = query.toLowerCase();
            menuItems = menuItems.stream()
                    .filter(item -> (item.getName() != null && item.getName().toLowerCase().contains(lowerQuery)) ||
                            (item.getDescription() != null && item.getDescription().toLowerCase().contains(lowerQuery)))
                    .collect(Collectors.toList());
        }
        
        // Filter by vegetarian
        if (isVeg != null) {
            menuItems = menuItems.stream()
                    .filter(item -> item.isVegetarian() == isVeg)
                    .collect(Collectors.toList());
        }
        
        // Filter by price range
        if (minPrice != null) {
            menuItems = menuItems.stream()
                    .filter(item -> item.getPrice() >= minPrice)
                    .collect(Collectors.toList());
        }
        
        if (maxPrice != null) {
            menuItems = menuItems.stream()
                    .filter(item -> item.getPrice() <= maxPrice)
                    .collect(Collectors.toList());
        }
        
        return ResponseEntity.ok(menuItems);
    }
    
    @GetMapping("/by-menu/{menuId}")
    public ResponseEntity<List<MenuItemDTO>> getMenuItemsByMenuId(@PathVariable Long menuId) {
        return ResponseEntity.ok(menuService.getMenuItemsByMenuId(menuId));
    }
    
    @PostMapping("/{id}/image")
    public ResponseEntity<MenuItemDTO> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        // This would typically call a service method to handle the file upload
        // For now, we'll just return the menu item
        return ResponseEntity.ok(menuService.getMenuItemById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + id)));
    }
}
