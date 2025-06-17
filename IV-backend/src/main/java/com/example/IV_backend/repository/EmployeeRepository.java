package com.example.IV_backend.repository;

import com.example.IV_backend.model.Employee;
import com.example.IV_backend.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository  extends JpaRepository<Employee , Long> {
    List<Employee> findByUserId(Long userId);
    List<Employee> findByUserIdAndNameContainingIgnoreCase(Long userId, String keyword);
}
