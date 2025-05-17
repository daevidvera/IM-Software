package com.example.IV_backend.controller;

import com.example.IV_backend.dto.InventoryDto;
import com.example.IV_backend.services.InventoryService;
import com.example.IV_backend.services.UserServices;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/dashboard/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    private final UserServices userServices;

    // Create inventory for authenticated user
    @PostMapping
    public ResponseEntity<InventoryDto> createInventory(
            @RequestBody InventoryDto inventoryDto,
            Authentication auth) {
        String username = auth.getName();
        Long userId = userServices.getUserIdByUsername(username);
        InventoryDto savedInventory = inventoryService.createInventory(userId, inventoryDto);
        return ResponseEntity.ok(savedInventory);
    }

    // Get a single inventory item by ID
    @GetMapping("/item/{inventoryId}")
    public ResponseEntity<InventoryDto> getInventoryById(@PathVariable Long inventoryId) {
        InventoryDto inventory = inventoryService.getInventoryById(inventoryId);
        return ResponseEntity.ok(inventory);
    }

    // Get all inventory items for the authenticated user
    @GetMapping("/user")
    public ResponseEntity<List<InventoryDto>> getAllInventoryForUser(Authentication auth) {
        String username = auth.getName();
        Long userId = userServices.getUserIdByUsername(username);
        List<InventoryDto> inventoryList = inventoryService.getAllInventoryForUser(userId);
        return ResponseEntity.ok(inventoryList);
    }

    // Update inventory item
    @PutMapping("/{inventoryId}")
    public ResponseEntity<InventoryDto> updateInventory(
            @PathVariable Long inventoryId,
            @RequestBody InventoryDto inventoryDto) {
        InventoryDto updated = inventoryService.updateInventory(inventoryId, inventoryDto);
        return ResponseEntity.ok(updated);
    }

    // Delete inventory item
    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long inventoryId) {
        inventoryService.deleteInventoryById(inventoryId);
        return ResponseEntity.noContent().build();
    }

    // Search inventory for the authenticated user
    @GetMapping("/search")
    public ResponseEntity<List<InventoryDto>> searchInventory(
            @RequestParam String keyword,
            Authentication auth) {
        String username = auth.getName();
        Long userId = userServices.getUserIdByUsername(username);
        List<InventoryDto> results = inventoryService.searchInventory(userId, keyword);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        // Validate file type
        String contentType = file.getContentType();
        if (!(contentType.equals("image/jpeg") || contentType.equals("image/png"))) {
            return ResponseEntity.badRequest().body("Invalid file type.");
        }

        // Generate safe filename
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Store in filesystem (e.g., /uploads)
        Path uploadPath = Paths.get("uploads");
        Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return file access URL
        return ResponseEntity.ok("http://localhost:8080/uploads/" + filename);
    }

}
