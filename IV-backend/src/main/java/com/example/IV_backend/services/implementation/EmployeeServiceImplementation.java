package com.example.IV_backend.services.implementation;

import com.example.IV_backend.dto.EmployeeDto;
import com.example.IV_backend.dto.InventoryDto;
import com.example.IV_backend.exception.ResourceNotFoundException;
import com.example.IV_backend.mapper.EmployeeMapper;
import com.example.IV_backend.mapper.InventoryMapper;
import com.example.IV_backend.model.Employee;
import com.example.IV_backend.model.InventoryItem;
import com.example.IV_backend.model.User_app;
import com.example.IV_backend.repository.EmployeeRepository;
import com.example.IV_backend.repository.UserRepository;
import com.example.IV_backend.services.EmployeeServices;
import jakarta.websocket.OnError;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class EmployeeServiceImplementation  implements EmployeeServices {

   private final EmployeeRepository employeeRepository;
   private final UserRepository userRepository;

    @Override
    public EmployeeDto createEmployee (Long userId, EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapEmployee(employeeDto);

        User_app user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        employee.setUser(user);
        Employee savedEmployee = employeeRepository.save(employee);
        return  EmployeeMapper.mapEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee doesnt' exist with given id"));

        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setEmployeePicture(employeeDto.getEmployeePicture());

        Employee savedEmployee = employeeRepository.save(employee);
        return  EmployeeMapper.mapEmployeeDto(savedEmployee);
    }

    @Override

    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->  new ResourceNotFoundException("Employee not found"));

        return EmployeeMapper.mapEmployeeDto(employee);
    }

    @Override
     public List<EmployeeDto> getAllEmployeesForUser(Long userId) {
        List<Employee> employee = employeeRepository.findByUserId(userId);
        return employee.stream()
                .map(EmployeeMapper:: mapEmployeeDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));
        employeeRepository.delete(employee);
    }

    @Override
    public List<EmployeeDto> searchEmployee(Long userId, String keyword) {
        List<Employee> employees = employeeRepository
                .findByUserIdAndNameContainingIgnoreCase(userId, keyword);

        return employees.stream()
                .map(EmployeeMapper::mapEmployeeDto)
                .collect(Collectors.toList());
    }







}
