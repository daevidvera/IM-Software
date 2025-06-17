package com.example.IV_backend.mapper;

import com.example.IV_backend.dto.EmployeeDto;
import com.example.IV_backend.model.Employee;

public class EmployeeMapper {

    public static EmployeeDto mapEmployeeDto (Employee employee) {
        return new EmployeeDto (
                employee.getId(),                   // employeeID
                employee.getName(),                 // name
                employee.getEmail(),                //
                employee.getEmployeePicture(),      //
                employee.getSalary()                // salary
        );
    }


    // new item
    public static Employee mapEmployee (EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getEmployeeID());
        employee.setName(employeeDto.getName());
        employee.setEmployeePicture(employeeDto.getPhotoURL());
        employee.setEmail(employeeDto.getEmail());
        employee.setSalary(employeeDto.getSalary());

        return employee;
    }
}
