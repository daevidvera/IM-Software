package com.example.IV_backend.mapper;

import com.example.IV_backend.dto.EmployeeDto;
import com.example.IV_backend.model.Employee;

public class EmployeeMapper {

    public static EmployeeDto mapEmployeeDto (Employee employee) {
        return new EmployeeDto (
                employee.getId(),
                employee.getEmail(),
                employee.getName(),
                employee.getEmployeePicture()
        );
    }


    // new item
    public static Employee mapEmployee (EmployeeDto employeeDto) {
        Employee employee = new Employee();

        employee.setName(employeeDto.getName());
        employee.setId(employeeDto.getId());
        employee.setEmployeePicture(employeeDto.getEmployeePicture());
        employee.setEmail(employeeDto.getEmail());

        return employee;
    }
}
