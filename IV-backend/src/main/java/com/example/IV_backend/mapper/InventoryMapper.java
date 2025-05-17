package com.example.IV_backend.mapper;

import com.example.IV_backend.dto.InventoryDto;
import com.example.IV_backend.model.InventoryItem;

public class InventoryMapper {

    public static InventoryDto mapInventoryDto(InventoryItem inventory) {
        return new InventoryDto(
                inventory.getId(),
                inventory.getName(),
                inventory.getPhotoUrl(),
                inventory.getCategory(),
                inventory.getPriceBought(),
                inventory.getSellingPrice(),
                inventory.getQuantity()
        );
    }

    // new item
    public static InventoryItem mapInventoryItem(InventoryDto dto) {
        InventoryItem item = new InventoryItem();
        item.setName(dto.getProductName());
        item.setPhotoUrl(dto.getPhotoURL());
        item.setCategory(dto.getCategory());
        item.setPriceBought(dto.getPriceBought());
        item.setSellingPrice(dto.getSellingPrice());
        item.setQuantity(dto.getQuantity());

        return item;
    }


    public static InventoryItem mapInventoryItemForUpdate(InventoryDto dto) {
        InventoryItem item = mapInventoryItem(dto);
        item.setId(dto.getProductID()); // only when updating
        return item;
    }
}

