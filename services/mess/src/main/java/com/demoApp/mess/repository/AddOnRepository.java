package com.demoApp.mess.repository;

import com.demoApp.mess.entity.AddOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddOnRepository extends JpaRepository<AddOn, Long> {
    List<AddOn> findByMenuItemId(Long menuItemId);
} 