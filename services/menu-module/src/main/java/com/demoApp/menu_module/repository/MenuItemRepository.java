package com.demoApp.menu_module.repository;

import com.demoApp.menu_module.entity.MenuItem;
import com.demoApp.menu_module.entity.MenuItemType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    
    @Query("SELECT mi FROM MenuItem mi WHERE mi.menu.id = :menuId ORDER BY mi.displayOrder ASC")
    List<MenuItem> findByMenuIdOrderByDisplayOrderAsc(Long menuId);
    
    @Query("SELECT mi FROM MenuItem mi WHERE mi.menu.id = :menuId AND mi.isVegetarian = :isVegetarian")
    List<MenuItem> findByMenuIdAndIsVegetarian(Long menuId, boolean isVegetarian);

    @Query("SELECT mi FROM MenuItem mi WHERE mi.menu.id = :menuId AND mi.category = :category")
    List<MenuItem> findByMenuIdAndCategory(Long menuId, MenuItemType category);
    
    @Query("SELECT mi FROM MenuItem mi WHERE mi.menu.ownerId = :ownerId AND mi.available = :available")
    List<MenuItem> findByMenu_Owner_IdAndAvailable(Long ownerId, boolean available);
    
    @Query("SELECT DISTINCT mi.category FROM MenuItem mi WHERE mi.menu.ownerId = :ownerId")
    List<String> findDistinctCategoriesByMenu_Owner_Id(Long ownerId);
    
    @Query("SELECT mi FROM MenuItem mi WHERE mi.menu.ownerId = :ownerId AND mi.isVegetarian = true")
    List<MenuItem> findByMenu_Owner_IdAndIsVegetarianTrue(Long ownerId);
    
    @Query("SELECT mi FROM MenuItem mi WHERE mi.menu.ownerId = :ownerId AND mi.category IN ('ADDON_CHAPATI', 'ADDON_ROTI', 'ADDON_RICE', 'ADDON_DAL', 'ADDON_GULAB_JAMUN')")
    List<MenuItem> findAddOnItemsByOwnerId(Long ownerId);
    
    @Query("SELECT mi FROM MenuItem mi WHERE mi.menu.id = :menuId")
    List<MenuItem> findByMenuId(Long menuId);
}