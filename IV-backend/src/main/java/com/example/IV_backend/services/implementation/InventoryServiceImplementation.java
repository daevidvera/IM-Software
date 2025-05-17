package com.example.IV_backend.services.implementation;

import com.example.IV_backend.dto.InventoryDto;
import com.example.IV_backend.exception.ResourceNotFoundException;
import com.example.IV_backend.mapper.InventoryMapper;
import com.example.IV_backend.model.InventoryItem;
import com.example.IV_backend.model.User_app;
import com.example.IV_backend.repository.InventoryRepository;
import com.example.IV_backend.repository.UserRepository;
import com.example.IV_backend.services.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryServiceImplementation implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;

    @Override
    public InventoryDto createInventory(Long userId, InventoryDto inventoryDto) {
        InventoryItem inventoryItem = InventoryMapper.mapInventoryItem(inventoryDto);
        User_app user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        inventoryItem.setUser(user);
        InventoryItem savedInventoryItem = inventoryRepository.save(inventoryItem);
        return InventoryMapper.mapInventoryDto(savedInventoryItem);
    }

    @Override
    public InventoryDto updateInventory(Long inventoryId, InventoryDto inventoryDto) {
        InventoryItem inventoryItem = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        inventoryItem.setName(inventoryDto.getProductName());
        inventoryItem.setCategory(inventoryDto.getCategory());
        inventoryItem.setPhotoUrl(inventoryDto.getPhotoURL());
        inventoryItem.setPriceBought(inventoryDto.getPriceBought());
        inventoryItem.setSellingPrice(inventoryDto.getSellingPrice());
        inventoryItem.setQuantity(inventoryDto.getQuantity());

        InventoryItem savedInventoryItem = inventoryRepository.save(inventoryItem);
        return InventoryMapper.mapInventoryDto(savedInventoryItem);
    }

    @Override
    public InventoryDto getInventoryById(Long inventoryId) {
        InventoryItem inventoryItem = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory doesn't exist with the given ID"));
        return InventoryMapper.mapInventoryDto(inventoryItem);
    }

    @Override
    public List<InventoryDto> getAllInventoryForUser(Long userId) {
        List<InventoryItem> items = inventoryRepository.findByUserId(userId);
        return items.stream()
                .map(InventoryMapper::mapInventoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteInventoryById(Long inventoryId) {
        InventoryItem item = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));
        inventoryRepository.delete(item);
    }

    @Override
    public List<InventoryDto> searchInventory(Long userId, String keyword) {
        List<InventoryItem> items = inventoryRepository
                .findByUserIdAndNameContainingIgnoreCase(userId, keyword);

        return items.stream()
                .map(InventoryMapper::mapInventoryDto)
                .collect(Collectors.toList());
    }

}
