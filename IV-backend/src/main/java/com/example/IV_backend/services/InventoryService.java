package com.example.IV_backend.services;

import com.example.IV_backend.dto.InventoryDto;
import java.util.List;

public interface InventoryService {

    // Create new inventory item
    InventoryDto createInventory(Long userId, InventoryDto inventoryDto);

    // Update item by ID
    InventoryDto updateInventory(Long inventoryId, InventoryDto inventoryDto);

    // Get item by ID
    InventoryDto getInventoryById(Long inventoryId);

    // Get all inventory items for a specific user
    List<InventoryDto> getAllInventoryForUser(Long userId);

    //  search/filter by name or category
    List<InventoryDto> searchInventory(Long userId, String keyword);

    //  Delete by ID
    void deleteInventoryById(Long inventoryId);
}
