package com.example.Employee_Management_System.service;

import com.example.Employee_Management_System.dto.EmployeeRequestDto;
import com.example.Employee_Management_System.entity.Department;
import com.example.Employee_Management_System.entity.Employee;
import com.example.Employee_Management_System.exception.custom.DepartmentNotFoundException;
import com.example.Employee_Management_System.repository.DepartmentRepository;
import com.example.Employee_Management_System.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;



    public Employee createEmployee(EmployeeRequestDto dto) {

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() ->  new DepartmentNotFoundException("Department not found with ID: " + dto.getDepartmentId()));



        Employee reportingManager = null;
        if (dto.getManagerId() != null) {
            reportingManager = employeeRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new EntityNotFoundException("Reporting manager not found with ID: " + dto.getManagerId()));
        }


        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setDateOfBirth(dto.getDob());
        employee.setSalary(dto.getSalary());
        employee.setAddress(dto.getAddress());
        employee.setRole(dto.getRole());
        employee.setJoiningDate(dto.getJoiningDate());
        employee.setYearlyBonusPercentage(dto.getBonusPercentage());
        employee.setDepartment(department);
        employee.setReportingManager(reportingManager);

        // Save and return
        return employeeRepository.save(employee);
    }
}
