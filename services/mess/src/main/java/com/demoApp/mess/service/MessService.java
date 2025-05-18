package com.demoApp.mess.service;

import com.demoApp.mess.dto.*;
import com.demoApp.mess.entity.Mess;
import com.demoApp.mess.exception.DuplicateResourceException;
import com.demoApp.mess.exception.ResourceNotFoundException;
import com.demoApp.mess.repository.MessRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MessService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MessService.class);

    private final MessRepository messRepository;
    private final PasswordEncoder passwordEncoder;
    
    public MessService(MessRepository messRepository, PasswordEncoder passwordEncoder) {
        this.messRepository = messRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<MessDTO> getAllMesses() {
        log.debug("Getting all messes");
        return messRepository.findAll().stream()
                .map(this::convertToMessDTO)
                .collect(Collectors.toList());
    }
    
    private MessDTO convertToMessDTO(Mess mess) {
        MessDTO dto = new MessDTO();
        dto.setId(mess.getId());
        dto.setName(mess.getName());
        dto.setEmail(mess.getEmail());
        dto.setContactNumber(mess.getContactNumber());
        dto.setLocation(mess.getLocation());
        dto.setApproved(mess.isApproved());
        return dto;
    }
    
    public List<MessDTO> getApprovedMesses() {
        log.debug("Getting approved messes");
        return messRepository.findByApprovedTrue().stream()
                .map(this::convertToMessDTO)
                .collect(Collectors.toList());
    }
    
    public MessDTO getMessById(Long id) {
        log.debug("Getting mess with ID: {}", id);
        Mess mess = messRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mess not found with ID: " + id));
        return convertToMessDTO(mess);
    }
    
    @Transactional
    public MessDTO createMess(MessCreateDTO createDTO) {
        log.debug("Creating new mess: {}", createDTO.getName());
        
        // Check if mess with same email already exists
        if (messRepository.findByEmail(createDTO.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Mess with email " + createDTO.getEmail() + " already exists");
        }
        
        Mess mess = new Mess();
        mess.setName(createDTO.getName());
        mess.setEmail(createDTO.getEmail());
        mess.setPassword(passwordEncoder.encode(createDTO.getPassword()));
        mess.setContactNumber(createDTO.getContactNumber());
        mess.setLocation(createDTO.getLocation());
        mess.setDescription(createDTO.getDescription());
        mess.setCuisineType(createDTO.getCuisineType());
        
        // Set operational hours
        if (createDTO.getOpeningTime() != null) {
            mess.setOpeningTime(LocalTime.parse(createDTO.getOpeningTime()));
        }
        if (createDTO.getClosingTime() != null) {
            mess.setClosingTime(LocalTime.parse(createDTO.getClosingTime()));
        }
        
        // Set default values
        mess.setApproved(false);
        mess.setActive(true);
        mess.setCreatedAt(LocalDateTime.now());
        mess.setUpdatedAt(LocalDateTime.now());
        
        Mess savedMess = messRepository.save(mess);
        log.info("Created new mess with ID: {}", savedMess.getId());
        
        return convertToMessDTO(savedMess);
    }
    
    @Transactional
    public MessDTO updateMess(Long id, MessUpdateDTO updateDTO) {
        log.debug("Updating mess with ID: {}", id);
        
        Mess mess = messRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mess not found with ID: " + id));
        
        if (updateDTO.getName() != null) {
            mess.setName(updateDTO.getName());
        }
        if (updateDTO.getContactNumber() != null) {
            mess.setContactNumber(updateDTO.getContactNumber());
        }
        if (updateDTO.getLocation() != null) {
            mess.setLocation(updateDTO.getLocation());
        }
        if (updateDTO.getDescription() != null) {
            mess.setDescription(updateDTO.getDescription());
        }
        if (updateDTO.getCuisineType() != null) {
            mess.setCuisineType(updateDTO.getCuisineType());
        }
        if (updateDTO.getOpeningTime() != null) {
            mess.setOpeningTime(updateDTO.getOpeningTime());
        }
        if (updateDTO.getClosingTime() != null) {
            mess.setClosingTime(updateDTO.getClosingTime());
        }
        
        mess.setUpdatedAt(LocalDateTime.now());
        
        Mess updatedMess = messRepository.save(mess);
        log.info("Updated mess with ID: {}", updatedMess.getId());
        
        return convertToMessDTO(updatedMess);
    }
    
    @Transactional
    public void deleteMess(Long id) {
        log.debug("Deleting mess with ID: {}", id);
        
        Mess mess = messRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mess not found with ID: " + id));
        
        messRepository.delete(mess);
        log.info("Deleted mess with ID: {}", id);
    }
    
    public List<MessDTO> getActiveMesses() {
        log.debug("Getting active messes");
        return messRepository.findByActiveTrue().stream()
                .map(this::convertToMessDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public MessDTO deactivateMess(Long id) {
        log.debug("Deactivating mess with ID: {}", id);
        
        Mess mess = messRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mess not found with ID: " + id));
        
        mess.setActive(false);
        mess.setUpdatedAt(LocalDateTime.now());
        
        Mess updatedMess = messRepository.save(mess);
        log.info("Deactivated mess with ID: {}", updatedMess.getId());
        
        return convertToMessDTO(updatedMess);
    }
    
    @Transactional
    public MessDTO activateMess(Long id) {
        log.debug("Activating mess with ID: {}", id);
        
        Mess mess = messRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mess not found with ID: " + id));
        
        mess.setActive(true);
        mess.setUpdatedAt(LocalDateTime.now());
        
        Mess updatedMess = messRepository.save(mess);
        log.info("Activated mess with ID: {}", updatedMess.getId());
        
        return convertToMessDTO(updatedMess);
    }
    
    @Transactional
    public MessDTO approveMess(Long id) {
        log.debug("Approving mess with ID: {}", id);
        
        Mess mess = messRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mess not found with ID: " + id));
        
        mess.setApproved(true);
        mess.setUpdatedAt(LocalDateTime.now());
        
        Mess updatedMess = messRepository.save(mess);
        log.info("Approved mess with ID: {}", updatedMess.getId());
        
        return convertToMessDTO(updatedMess);
    }
    
    @Transactional
    public MenuItemDTO createMenuItem(Long messId, MenuItemCreateDTO createDTO) {
        log.debug("Creating menu item for mess with ID: {}", messId);
        
        // Check if mess exists
        if (!messRepository.existsById(messId)) {
            throw new ResourceNotFoundException("Mess not found with ID: " + messId);
        }
        
        // In a real implementation, you would create and save the menu item
        // This is a simplified example
        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(1L); // This would be the actual ID from the saved entity
        dto.setName(createDTO.getName());
        dto.setDescription(createDTO.getDescription());
        dto.setPrice(createDTO.getPrice().doubleValue());
        dto.setVegetarian(createDTO.isVegetarian());
        dto.setAvailable(true);
        
        log.info("Created menu item for mess with ID: {}", messId);
        
        return dto;
    }
    
    @Transactional
    public MenuItemDTO updateMenuItem(Long messId, Long menuItemId, MenuItemUpdateDTO request) {
        log.debug("Updating menu item with ID: {} for mess with ID: {}", menuItemId, messId);
        
        // In a real implementation, you would fetch the menu item and update it
        // This is a simplified example
        
        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(menuItemId);
        dto.setName(request.getName());
        dto.setDescription(request.getDescription());
        dto.setPrice(request.getPrice());
        dto.setVegetarian(request.getIsVegetarian());
        dto.setAvailable(request.getIsAvailable());
        
        log.info("Updated menu item with ID: {} for mess with ID: {}", menuItemId, messId);
        
        return dto;
    }
    
    public List<MenuItemDTO> getSampleMenuItems() {
        List<MenuItemDTO> menuItems = new ArrayList<>();
        
        // Sample menu item 1
        MenuItemDTO item1 = new MenuItemDTO();
        item1.setId(1L);
        item1.setName("Veg Thali");
        item1.setDescription("Complete vegetarian meal");
        item1.setPrice(120.0);
        item1.setVegetarian(true);
        item1.setAvailable(true);
        menuItems.add(item1);
        
        // Sample menu item 2
        MenuItemDTO item2 = new MenuItemDTO();
        item2.setId(2L);
        item2.setName("Non-Veg Thali");
        item2.setDescription("Complete non-vegetarian meal");
        item2.setPrice(150.0);
        item2.setVegetarian(false);
        item2.setAvailable(true);
        menuItems.add(item2);
        
        return menuItems;
    }

    public MessAnalyticsDTO getAnalytics(Long messId, LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Getting analytics for mess with ID: {} from {} to {}", messId, startDate, endDate);
        
        // Check if mess exists
        messRepository.findById(messId)
                .orElseThrow(() -> new ResourceNotFoundException("Mess not found with ID: " + messId));
        
        // In a real implementation, you would calculate analytics based on actual data
        // For this example, we'll just return sample data
        
        Map<String, Long> categoryDistribution = new HashMap<>();
        categoryDistribution.put("Main Course", 10L);
        categoryDistribution.put("Starters", 5L);
        categoryDistribution.put("Desserts", 3L);
        categoryDistribution.put("Beverages", 2L);
        
        Map<String, Long> priceRangeDistribution = new HashMap<>();
        priceRangeDistribution.put("0-50", 3L);
        priceRangeDistribution.put("51-100", 10L);
        priceRangeDistribution.put("101-200", 7L);
        
        MessAnalyticsDTO analyticsDTO = new MessAnalyticsDTO();
        analyticsDTO.setTotalMenuItems(20);
        analyticsDTO.setAvailableItems(15);
        analyticsDTO.setCategoryDistribution(categoryDistribution);
        analyticsDTO.setPriceRangeDistribution(priceRangeDistribution);
        analyticsDTO.setVegetarianRatio(0.6);
        
        return analyticsDTO;
    }
    
    public List<MenuItemDTO> getMessMenu(Long messId) {
        log.debug("Getting menu items for mess with ID: {}", messId);
        
        // Check if mess exists
        if (!messRepository.existsById(messId)) {
            throw new ResourceNotFoundException("Mess not found with ID: " + messId);
        }
        
        // In a real implementation, you would fetch the menu items from a repository
        // This is a simplified example returning sample menu items
        return getSampleMenuItems();
    }
    
    @Transactional
    public MenuItemDTO addMenuItem(Long messId, MenuItemCreateDTO createDTO) {
        log.debug("Adding menu item to mess with ID: {}", messId);
        
        // Check if mess exists
        if (!messRepository.existsById(messId)) {
            throw new ResourceNotFoundException("Mess not found with ID: " + messId);
        }
        
        // In a real implementation, you would create and save the menu item
        // This is a simplified example
        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(1L); // This would be the actual ID from the saved entity
        dto.setName(createDTO.getName());
        dto.setDescription(createDTO.getDescription());
        dto.setPrice(createDTO.getPrice().doubleValue());
        dto.setVegetarian(createDTO.isVegetarian());
        dto.setSpicy(createDTO.isSpicy());
        dto.setAvailable(true);
        dto.setPreparationTime(createDTO.getPreparationTime());
        
        log.info("Added menu item to mess with ID: {}", messId);
        
        return dto;
    }
}
