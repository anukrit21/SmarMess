package com.demoApp.order.repository;

import com.demoApp.order.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByMessId(Long messId);
    List<Menu> findByIdIn(List<Long> ids);
} 