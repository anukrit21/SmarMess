package com.demoApp.mess.mapper;

import com.demoApp.mess.dto.AddOnDTO;
import com.demoApp.mess.dto.MenuDTO;
import com.demoApp.mess.dto.MenuItemDTO;
import com.demoApp.mess.entity.AddOn;
import com.demoApp.mess.entity.Menu;
import com.demoApp.mess.entity.MenuItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuMapper {

    public enum MealType {
        BREAKFAST, LUNCH, DINNER, SNACK
    }
    
    private MenuDTO.MealType convertMealType(Menu.MealType mealType) {
        if (mealType == null) {
            return null;
        }
        
        switch (mealType) {
            case BREAKFAST:
                return MenuDTO.MealType.BREAKFAST;
            case LUNCH:
                return MenuDTO.MealType.LUNCH;
            case DINNER:
                return MenuDTO.MealType.DINNER;
            case SNACK:
                return MenuDTO.MealType.SNACK;
            default:
                throw new IllegalArgumentException("Unknown meal type: " + mealType);
        }
    }

    public MenuDTO toDTO(Menu menu) {
        if (menu == null) {
            return null;
        }

        MenuDTO dto = new MenuDTO();
        dto.setId(menu.getId());
        dto.setName(menu.getName());
        dto.setDescription(menu.getDescription());
        dto.setActive(menu.getAvailable()); // Using getAvailable as isActive
        dto.setMessId(menu.getMess() != null ? menu.getMess().getId() : null);
        
        if (menu.getAvailableAddOns() != null) {
            dto.setMenuItems(menu.getAvailableAddOns().stream()
                    .map(this::toMenuItemDTO)
                    .collect(Collectors.toList()));
        }
        
        dto.setPrice(menu.getBasePrice());
        dto.setMealType(menu.getMealType() != null ? convertMealType(menu.getMealType()) : null);
        dto.setAvailable(menu.getAvailable());
        dto.setCreatedBy(null);
        dto.setUpdatedBy(null);
        dto.setCreatedAt(null);
        dto.setUpdatedAt(null);
        
        return dto;
    }
    
    private MenuItemDTO toMenuItemDTO(AddOn addOn) {
        if (addOn == null) {
            return null;
        }
        
        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(addOn.getId());
        dto.setName(addOn.getName());
        dto.setDescription("Add-on item");
        dto.setPrice(addOn.getPrice().doubleValue());
        dto.setVegetarian(false); // Default value since AddOn doesn't have isVegetarian
        dto.setAvailable(addOn.getIsAvailable());
        
        return dto;
    }

    public MenuItemDTO toDTO(MenuItem menuItem) {
        if (menuItem == null) {
            return null;
        }

        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(menuItem.getId());
        dto.setName(menuItem.getName());
        dto.setDescription(menuItem.getDescription());
        dto.setPrice(menuItem.getPrice().doubleValue());
        dto.setImageUrl(menuItem.getImageUrl());
        dto.setVegetarian(menuItem.getIsVegetarian());
        dto.setSpicy(menuItem.getIsSpicy());
        dto.setAvailable(menuItem.getIsAvailable());
        dto.setPreparationTime(menuItem.getPreparationTime());
        dto.setMenuId(menuItem.getMenu() != null ? menuItem.getMenu().getId() : null);
        
        if (menuItem.getAddOns() != null) {
            dto.setAddOns(menuItem.getAddOns().stream()
                    .map(this::toAddOnDTO)
                    .collect(Collectors.toList()));
        }
        
        dto.setCreatedBy(menuItem.getCreatedBy());
        dto.setUpdatedBy(menuItem.getUpdatedBy());
        dto.setCreatedAt(menuItem.getCreatedAt());
        dto.setUpdatedAt(menuItem.getUpdatedAt());
        
        return dto;
    }

    public AddOnDTO toAddOnDTO(AddOn addOn) {
        if (addOn == null) {
            return null;
        }

        AddOnDTO dto = new AddOnDTO();
        dto.setId(addOn.getId());
        dto.setName(addOn.getName());
        dto.setDescription(addOn.getDescription());
        dto.setPrice(addOn.getPrice() != null ? addOn.getPrice().doubleValue() : null);
        dto.setAvailable(addOn.getIsAvailable());
        dto.setMenuItemId(addOn.getMenuItem() != null ? addOn.getMenuItem().getId() : null);
        dto.setCreatedBy(addOn.getCreatedBy() != null ? addOn.getCreatedBy().toString() : null);
        dto.setUpdatedBy(addOn.getUpdatedBy() != null ? addOn.getUpdatedBy().toString() : null);
        dto.setCreatedAt(addOn.getCreatedAt());
        dto.setUpdatedAt(addOn.getUpdatedAt());
        return dto;
    }
    
    // Alias for toAddOnDTO to support code that expects toDTO
    public AddOnDTO toDTO(AddOn addOn) {
        return toAddOnDTO(addOn);
    }

    public List<MenuDTO> toDTOList(List<Menu> menus) {
        if (menus == null) {
            return null;
        }
        return menus.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<MenuItemDTO> toMenuItemDTOList(List<MenuItem> menuItems) {
        if (menuItems == null) {
            return null;
        }
        return menuItems.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AddOnDTO> toAddOnDTOList(List<AddOn> addOns) {
        if (addOns == null) {
            return null;
        }
        return addOns.stream()
                .map(this::toAddOnDTO)
                .collect(Collectors.toList());
    }
}
