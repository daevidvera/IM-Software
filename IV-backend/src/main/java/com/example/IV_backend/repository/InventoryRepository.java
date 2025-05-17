package com.example.IV_backend.repository;

import com.example.IV_backend.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository  extends JpaRepository<InventoryItem, Long> {

    List<InventoryItem> findByUserId(Long userId);
    List<InventoryItem> findByUserIdAndNameContainingIgnoreCase(Long userId, String keyword);


}
