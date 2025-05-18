package com.demoApp.owner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demoApp.owner.entity.Mess;
import com.demoApp.owner.entity.Owner;

import java.util.List;

@Repository
public interface MessRepository extends JpaRepository<Mess, Long> {
    List<Mess> findByOwner(Owner owner);
    List<Mess> findByOwnerAndAvailable(Owner owner, boolean available);
    List<Mess> findByAvailable(boolean available);
    List<Mess> findByNameContainingIgnoreCase(String name);
} 