package com.example.IV_backend.services;

import com.example.IV_backend.dto.EmployeeDto;
import com.example.IV_backend.dto.InventoryDto;
import com.example.IV_backend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeServices  {

    EmployeeDto createEmployee (Long userId, EmployeeDto employeeDto);

    EmployeeDto updateEmployee (Long employeeId, EmployeeDto employeeDto);

    // Get employee by ID
    EmployeeDto getEmployeeById(Long employeeId);

    // Get all inventory items for a specific user
    List<EmployeeDto> getAllEmployeesForUser(Long userId);

    //  search/filter by name or category
    List<EmployeeDto> searchEmployee(Long userId, String keyword);

    //  Delete by ID
    void deleteEmployeeById(Long employeeId);
}
