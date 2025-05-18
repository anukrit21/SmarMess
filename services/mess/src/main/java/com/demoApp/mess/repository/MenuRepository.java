package com.demoApp.mess.repository;

import com.demoApp.mess.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    
    List<Menu> findByMess_Id(Long messId);
    
    List<Menu> findByMess_IdAndMealType(Long messId, Menu.MealType mealType);
    
    List<Menu> findByMess_IdAndAvailableTrue(Long messId);
    
    // Removed findAllCategories, as Menu has no 'category' field.
    
    @Query("SELECT m FROM Menu m WHERE " +
           "(:query IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(m.description) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "(:minPrice IS NULL OR m.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR m.price <= :maxPrice) AND " +
           "(:category IS NULL OR m.mealType = :category)")
    List<Menu> searchMenuItems(
            @Param("query") String query,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("category") Menu.MealType category);
    
}