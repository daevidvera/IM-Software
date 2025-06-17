package com.example.IV_backend.controller;

import com.example.IV_backend.dto.EmployeeDto;
import com.example.IV_backend.dto.InventoryDto;
import com.example.IV_backend.services.EmployeeServices;
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
@RequestMapping("/dashboard/employee")
public class EmployeeController {

    private final EmployeeServices employeeServices;
    private final UserServices userServices;

    // Create inventory for authenticated user
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(
            @RequestBody EmployeeDto employeeDto,
            Authentication auth) {
        String username = auth.getName();
        Long userId = userServices.getUserIdByUsername(username);
        EmployeeDto savedEmployee = employeeServices.createEmployee(userId, employeeDto);
        return ResponseEntity.ok(savedEmployee);
    }

    // Get a single inventory item by ID
    @GetMapping("/item/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long employeeId) {
        EmployeeDto employee = employeeServices.getEmployeeById(employeeId);
        return ResponseEntity.ok(employee);
    }

    // Get all inventory items for the authenticated user
    @GetMapping("/user")
    public ResponseEntity<List<EmployeeDto>> getAllEmployeesForUser(Authentication auth) {
        String username = auth.getName();
        Long userId = userServices.getUserIdByUsername(username);
        List<EmployeeDto> employeeList = employeeServices.getAllEmployeesForUser(userId);
        return ResponseEntity.ok(employeeList);
    }

    // Update inventory item
    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable Long employeeId,
            @RequestBody EmployeeDto employeeDto) {
        EmployeeDto updated = employeeServices.updateEmployee(employeeId, employeeDto);
        return ResponseEntity.ok(updated);
    }

    // Delete inventory item
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) {
        employeeServices.deleteEmployeeById(employeeId);
        return ResponseEntity.noContent().build();
    }

    // Search inventory for the authenticated user
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDto>> searchInventory(
            @RequestParam String keyword,
            Authentication auth) {
        String username = auth.getName();
        Long userId = userServices.getUserIdByUsername(username);
        List<EmployeeDto> results = employeeServices.searchEmployee(userId, keyword);
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
