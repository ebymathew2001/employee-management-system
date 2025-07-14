package com.example.Employee_Management_System.service;

import com.example.Employee_Management_System.dto.*;
import com.example.Employee_Management_System.entity.Department;
import com.example.Employee_Management_System.entity.Employee;
import com.example.Employee_Management_System.exception.custom.BusinessRuleViolationException;
import com.example.Employee_Management_System.exception.custom.DepartmentNotFoundException;
import com.example.Employee_Management_System.exception.custom.DuplicateEmployeeException;
import com.example.Employee_Management_System.exception.custom.ResourceNotFoundException;
import com.example.Employee_Management_System.repository.DepartmentRepository;
import com.example.Employee_Management_System.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;



    public EmployeeResponseDto createEmployee(EmployeeRequestDto dto) {

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() ->  new DepartmentNotFoundException("Department not found with ID: " + dto.getDepartmentId()));

        if (dto.getManagerId() == null && !department.getName().equalsIgnoreCase("Executive")) {
            throw new BusinessRuleViolationException("Employees outside the Executive department must have a reporting manager.");
        }

        boolean isDuplicate = employeeRepository.existsByNameAndDateOfBirthAndDepartmentId(
                dto.getName(), dto.getDob(), dto.getDepartmentId());

        if (isDuplicate) {
            throw new DuplicateEmployeeException("Employee with same name, date of birth, and department already exists.");
        }



        Employee reportingManager = null;
        if (dto.getManagerId() != null) {
            reportingManager = employeeRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Reporting manager not found with ID: " + dto.getManagerId()));

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

        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeResponseDto responseDto = new EmployeeResponseDto();
        responseDto.setId(savedEmployee.getId());
        responseDto.setName(savedEmployee.getName());
        responseDto.setDateOfBirth(savedEmployee.getDateOfBirth());
        responseDto.setSalary(savedEmployee.getSalary());
        responseDto.setAddress(savedEmployee.getAddress());
        responseDto.setRole(savedEmployee.getRole());
        responseDto.setJoiningDate(savedEmployee.getJoiningDate());
        responseDto.setYearlyBonusPercentage(savedEmployee.getYearlyBonusPercentage());


        if (savedEmployee.getDepartment() != null) {
            responseDto.setDepartmentId(savedEmployee.getDepartment().getId());
            responseDto.setDepartmentName(savedEmployee.getDepartment().getName());
        }

        if (savedEmployee.getReportingManager() != null) {
            responseDto.setReportingManagerId(savedEmployee.getReportingManager().getId());
            responseDto.setReportingManagerName(savedEmployee.getReportingManager().getName());
        }

        return responseDto;

    }

    public EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto dto) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + dto.getDepartmentId()));


        if (dto.getManagerId() == null && !department.getName().equalsIgnoreCase("Executive")) {
            throw new BusinessRuleViolationException("Employees outside the Executive department must have a reporting manager.");
        }

        if (dto.getManagerId() != null && dto.getManagerId().equals(id)) {
            throw new BusinessRuleViolationException("An employee cannot be their own reporting manager.");
        }


        boolean isDuplicate = employeeRepository.existsByNameAndDateOfBirthAndDepartmentIdAndIdNot(
                dto.getName(), dto.getDob(), dto.getDepartmentId(), id
        );
        if (isDuplicate) {
            throw new DuplicateEmployeeException("Another employee with same name, date of birth, and department already exists.");
        }

        Employee reportingManager = null;
        if (dto.getManagerId() != null) {
            reportingManager = employeeRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Reporting manager not found with ID: " + dto.getManagerId()));
        }

        employee.setName(dto.getName());
        employee.setDateOfBirth(dto.getDob());
        employee.setSalary(dto.getSalary());
        employee.setAddress(dto.getAddress());
        employee.setRole(dto.getRole());
        employee.setJoiningDate(dto.getJoiningDate());
        employee.setYearlyBonusPercentage(dto.getBonusPercentage());
        employee.setDepartment(department);
        employee.setReportingManager(reportingManager);

        Employee saved = employeeRepository.save(employee);

        EmployeeResponseDto responseDto = new EmployeeResponseDto();
        responseDto.setId(saved.getId());
        responseDto.setName(saved.getName());
        responseDto.setDateOfBirth(saved.getDateOfBirth());
        responseDto.setSalary(saved.getSalary());
        responseDto.setAddress(saved.getAddress());
        responseDto.setRole(saved.getRole());
        responseDto.setJoiningDate(saved.getJoiningDate());
        responseDto.setYearlyBonusPercentage(saved.getYearlyBonusPercentage());


        if (saved.getDepartment() != null) {
            responseDto.setDepartmentId(saved.getDepartment().getId());
            responseDto.setDepartmentName(saved.getDepartment().getName());
        }

        if (saved.getReportingManager() != null) {
            responseDto.setReportingManagerId(saved.getReportingManager().getId());
            responseDto.setReportingManagerName(saved.getReportingManager().getName());
        }

        return responseDto;


    }

    public EmployeeResponseDto updateEmployeeDepartment(Long id, UpdateEmployeeDepartmentDto dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

        Department oldDepartment = employee.getDepartment();

        Department newDepartment = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + dto.getDepartmentId()));

        if (oldDepartment.getDepartmentHead() != null &&
                oldDepartment.getDepartmentHead().getId().equals(employee.getId())) {
            oldDepartment.setDepartmentHead(null);
            departmentRepository.save(oldDepartment);
        }

        employee.setDepartment(newDepartment);
        Employee saved = employeeRepository.save(employee);

        EmployeeResponseDto response = new EmployeeResponseDto();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setDateOfBirth(saved.getDateOfBirth());
        response.setSalary(saved.getSalary());
        response.setAddress(saved.getAddress());
        response.setRole(saved.getRole());
        response.setJoiningDate(saved.getJoiningDate());
        response.setYearlyBonusPercentage(saved.getYearlyBonusPercentage());

        if (saved.getDepartment() != null) {
            response.setDepartmentId(saved.getDepartment().getId());
            response.setDepartmentName(saved.getDepartment().getName());
        }

        if (saved.getReportingManager() != null) {
            response.setReportingManagerId(saved.getReportingManager().getId());
            response.setReportingManagerName(saved.getReportingManager().getName());
        }

        return response;
    }

    public EmployeePageResponseDto getAllEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employeePage = employeeRepository.findAll(pageable);

        List<EmployeeResponseDto> employeeDtos = new ArrayList<>();

        for (Employee employee : employeePage.getContent()) {
            EmployeeResponseDto dto = new EmployeeResponseDto();
            dto.setId(employee.getId());
            dto.setName(employee.getName());
            dto.setDateOfBirth(employee.getDateOfBirth());
            dto.setSalary(employee.getSalary());
            dto.setAddress(employee.getAddress());
            dto.setRole(employee.getRole());
            dto.setJoiningDate(employee.getJoiningDate());
            dto.setYearlyBonusPercentage(employee.getYearlyBonusPercentage());

            if (employee.getDepartment() != null) {
                dto.setDepartmentId(employee.getDepartment().getId());
                dto.setDepartmentName(employee.getDepartment().getName());
            }

            if (employee.getReportingManager() != null) {
                dto.setReportingManagerId(employee.getReportingManager().getId());
                dto.setReportingManagerName(employee.getReportingManager().getName());
            }

            employeeDtos.add(dto);
        }

        EmployeePageResponseDto response = new EmployeePageResponseDto();
        response.setEmployees(employeeDtos);
        response.setCurrentPage(employeePage.getNumber());
        response.setTotalPages(employeePage.getTotalPages());
        response.setTotalItems(employeePage.getTotalElements());

        return response;
    }
    public Page<EmployeeLookupDto> getEmployeeLookups(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAllLookup(pageable);

}



}












