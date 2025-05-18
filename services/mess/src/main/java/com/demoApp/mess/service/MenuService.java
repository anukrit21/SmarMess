package com.demoApp.mess.service;

import com.demoApp.mess.dto.AddOnDTO;
import com.demoApp.mess.dto.MenuDTO;
import com.demoApp.mess.dto.MenuItemDTO;
import com.demoApp.mess.entity.AddOn;
import com.demoApp.mess.entity.Menu;
import com.demoApp.mess.entity.MenuItem;
import com.demoApp.mess.exception.ResourceNotFoundException;
import com.demoApp.mess.mapper.MenuMapper;
import com.demoApp.mess.repository.AddOnRepository;
import com.demoApp.mess.repository.MenuItemRepository;
import com.demoApp.mess.repository.MenuRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MenuService.class);
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;
    private final AddOnRepository addOnRepository;
    private final MenuMapper menuMapper;
    
    public MenuService(MenuRepository menuRepository, MenuItemRepository menuItemRepository, 
                      AddOnRepository addOnRepository, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
        this.addOnRepository = addOnRepository;
        this.menuMapper = menuMapper;
    }

    public List<MenuDTO> getAllMenus() {
        log.info("Fetching all menus");
        return menuMapper.toDTOList(menuRepository.findAll());
    }

    public Optional<MenuDTO> getMenuById(Long id) {
        log.info("Fetching menu with id: {}", id);
        return menuRepository.findById(id)
                .map(menuMapper::toDTO);
    }

    public List<MenuDTO> getMenusByMessId(Long messId) {
        log.info("Fetching menus for mess with id: {}", messId);
        return menuMapper.toDTOList(menuRepository.findByMess_Id(messId));
    }

    public List<MenuDTO> getMenusByMealType(Long messId, Menu.MealType mealType) {
        log.info("Fetching menus for mess {} with meal type: {}", messId, mealType);
        return menuMapper.toDTOList(menuRepository.findByMess_IdAndMealType(messId, mealType));
    }

    // If you have any methods that use searchMenuItems, ensure they use Menu.MealType for the category parameter.

    public List<MenuDTO> getAvailableMenus(Long messId) {
        log.info("Fetching available menus for mess: {}", messId);
        return menuMapper.toDTOList(menuRepository.findByMess_IdAndAvailableTrue(messId));
    }

    // public List<String> getAllCategories() {
    //     log.info("Fetching all menu categories");
    //     return menuRepository.findAllCategories();
    // }

    // public Page<MenuDTO> getPopularMenus(Pageable pageable) {
    //     log.info("Fetching popular menus");
    //     // This functionality is disabled because Menu has no orderCount property
    //     // return menuRepository.findTopByOrderCount(pageable)
    //     //         .map(menuMapper::toDTO);
    //     return Page.empty(pageable);
    // }

    @Transactional
    public MenuDTO createMenu(Menu menu) {
        log.info("Creating new menu: {}", menu.getName());
        return menuMapper.toDTO(menuRepository.save(menu));
    }

    @Transactional
    public MenuDTO updateMenu(Long id, MenuDTO menuDTO) {
        log.info("Updating menu with id: {}", id);
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", id));
        
        if (menuDTO.getName() != null) {
            menu.setName(menuDTO.getName());
        }
        if (menuDTO.getDescription() != null) {
            menu.setDescription(menuDTO.getDescription());
        }
        if (menuDTO.getIsActive() != null) {
            menu.setIsActive(menuDTO.getIsActive());
        }
        if (menuDTO.getAvailable() != null) {
            menu.setAvailable(menuDTO.getAvailable());
        }
        
        return menuMapper.toDTO(menuRepository.save(menu));
    }

    @Transactional
    public void deleteMenu(Long id) {
        log.info("Deleting menu with id: {}", id);
        if (!menuRepository.existsById(id)) {
            throw new ResourceNotFoundException("Menu", "id", id);
        }
        menuRepository.deleteById(id);
    }

    public List<MenuItemDTO> getAllMenuItems() {
        log.info("Fetching all menu items");
        // Use the correct toDTOList method for MenuItem entities
        List<MenuItem> menuItems = menuItemRepository.findAll();
        return menuItems.stream()
                .map(menuMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<MenuItemDTO> getMenuItemById(Long id) {
        log.info("Fetching menu item with id: {}", id);
        return menuItemRepository.findById(id)
                .map(menuMapper::toDTO);
    }

    public List<MenuItemDTO> getMenuItemsByMenuId(Long menuId) {
        log.info("Fetching menu items for menu: {}", menuId);
        // Use the correct toDTOList method for MenuItem entities
        List<MenuItem> menuItems = menuItemRepository.findByMenuId(menuId);
        return menuItems.stream()
                .map(menuMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MenuItemDTO createMenuItem(MenuItem menuItem) {
        log.info("Creating new menu item: {}", menuItem.getName());
        return menuMapper.toDTO(menuItemRepository.save(menuItem));
    }

    @Transactional
    public MenuItemDTO updateMenuItem(Long id, MenuItem menuItemDetails) {
        log.info("Updating menu item with id: {}", id);
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", id));
        
        menuItem.setName(menuItemDetails.getName());
        menuItem.setDescription(menuItemDetails.getDescription());
        menuItem.setPrice(menuItemDetails.getPrice());
        menuItem.setImageUrl(menuItemDetails.getImageUrl());
        menuItem.setIsVegetarian(menuItemDetails.getIsVegetarian());
        menuItem.setIsSpicy(menuItemDetails.getIsSpicy());
        menuItem.setIsAvailable(menuItemDetails.getIsAvailable());
        menuItem.setPreparationTime(menuItemDetails.getPreparationTime());
        
        return menuMapper.toDTO(menuItemRepository.save(menuItem));
    }

    @Transactional
    public void deleteMenuItem(Long id) {
        log.info("Deleting menu item with id: {}", id);
        if (!menuItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Menu item", "id", id);
        }
        menuItemRepository.deleteById(id);
    }

    public List<AddOnDTO> getAllAddOns() {
        log.info("Fetching all add-ons");
        // Use the correct toDTOList method for AddOn entities
        List<AddOn> addOns = addOnRepository.findAll();
        return addOns.stream()
                .map(menuMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<AddOnDTO> getAddOnById(Long id) {
        log.info("Fetching add-on with id: {}", id);
        return addOnRepository.findById(id)
                .map(menuMapper::toDTO);
    }

    public List<AddOnDTO> getAddOnsByMenuItemId(Long menuItemId) {
        log.info("Fetching add-ons for menu item: {}", menuItemId);
        // Use the correct toDTOList method for AddOn entities
        List<AddOn> addOns = addOnRepository.findByMenuItemId(menuItemId);
        return addOns.stream()
                .map(menuMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AddOnDTO createAddOn(AddOn addOn) {
        log.info("Creating new add-on: {}", addOn.getName());
        return menuMapper.toDTO(addOnRepository.save(addOn));
    }

    @Transactional
    public AddOnDTO updateAddOn(Long id, AddOn addOnDetails) {
        log.info("Updating add-on with id: {}", id);
        AddOn addOn = addOnRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Add-on", "id", id));
        
        addOn.setName(addOnDetails.getName());
        addOn.setDescription(addOnDetails.getDescription());
        addOn.setPrice(addOnDetails.getPrice());
        addOn.setIsAvailable(addOnDetails.getIsAvailable());
        
        return menuMapper.toDTO(addOnRepository.save(addOn));
    }

    @Transactional
    public void deleteAddOn(Long id) {
        log.info("Deleting add-on with id: {}", id);
        if (!addOnRepository.existsById(id)) {
            throw new ResourceNotFoundException("Add-on", "id", id);
        }
        addOnRepository.deleteById(id);
    }
}