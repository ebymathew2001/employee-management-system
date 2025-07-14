package com.example.Employee_Management_System.service;


import com.example.Employee_Management_System.dto.*;
import com.example.Employee_Management_System.entity.Department;
import com.example.Employee_Management_System.entity.Employee;
import com.example.Employee_Management_System.exception.custom.BusinessRuleViolationException;
import com.example.Employee_Management_System.exception.custom.DepartmentNotFoundException;
import com.example.Employee_Management_System.exception.custom.DuplicateDepartmentException;
import com.example.Employee_Management_System.exception.custom.ResourceNotFoundException;
import com.example.Employee_Management_System.repository.DepartmentRepository;
import com.example.Employee_Management_System.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final EmployeeRepository employeeRepository;

    public DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto) {

        if (departmentRepository.existsByName(requestDto.getName())) {
            throw new DuplicateDepartmentException("Department with name '" + requestDto.getName() + "' already exists");
        }

        Department department = new Department();
        department.setName(requestDto.getName());
        department.setCreationDate(LocalDate.now());

        Department saved = departmentRepository.save(department);

        DepartmentResponseDto responseDto = new DepartmentResponseDto();
        responseDto.setId(saved.getId());
        responseDto.setName(saved.getName());
        responseDto.setCreationDate(saved.getCreationDate());

        return responseDto;
    }

    public DepartmentResponseDto updateDepartment(Long id, DepartmentUpdateRequestDto dto) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + id));

        Optional<Department> existing = departmentRepository.findByName(dto.getName());
        if (existing.isPresent() && !existing.get().getId().equals(department.getId())) {
            throw new DuplicateDepartmentException("Department name '" + dto.getName() + "' is already used by another department.");
        }

        department.setName(dto.getName());

        if (dto.getDepartmentHeadId() != null) {
            Employee head = employeeRepository.findById(dto.getDepartmentHeadId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + dto.getDepartmentHeadId()));
            department.setDepartmentHead(head);
        }

        Department saved = departmentRepository.save(department);

        DepartmentResponseDto response = new DepartmentResponseDto();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setCreationDate(saved.getCreationDate());

        if (saved.getDepartmentHead() != null) {
            response.setDepartmentHeadId(saved.getDepartmentHead().getId());
            response.setDepartmentHeadName(saved.getDepartmentHead().getName());
        }

        return response;



    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + id));

        long employeeCount = employeeRepository.countByDepartmentId(id);

        if (employeeCount > 0) {
            throw new BusinessRuleViolationException("Cannot delete department. Employees are still assigned.");
        }

        departmentRepository.delete(department);
    }

    public DepartmentPageResponseDto getAllDepartments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Department> departmentPage = departmentRepository.findAll(pageable);

        List<DepartmentResponseDto> dtos = new ArrayList<>();

        for (Department department : departmentPage.getContent()) {
            DepartmentResponseDto dto = new DepartmentResponseDto();
            dto.setId(department.getId());
            dto.setName(department.getName());
            dto.setCreationDate(department.getCreationDate());

            if (department.getDepartmentHead() != null) {
                dto.setDepartmentHeadId(department.getDepartmentHead().getId());
                dto.setDepartmentHeadName(department.getDepartmentHead().getName());
            }

            dtos.add(dto);
        }

        DepartmentPageResponseDto response = new DepartmentPageResponseDto();
        response.setDepartments(dtos);
        response.setCurrentPage(departmentPage.getNumber());
        response.setTotalPages(departmentPage.getTotalPages());
        response.setTotalItems(departmentPage.getTotalElements());

        return response;
    }

    public DepartmentWithEmployeesResponseDto getDepartmentById(Long id, boolean expandEmployees) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + id));

        DepartmentWithEmployeesResponseDto response = new DepartmentWithEmployeesResponseDto();
        response.setId(department.getId());
        response.setName(department.getName());
        response.setCreationDate(department.getCreationDate());

        if (expandEmployees) {
            List<DepartmentWithEmployeesResponseDto.SimpleEmployeeDto> employeeDtos = new ArrayList<>();

            for (Employee employee : department.getEmployees()) {
                DepartmentWithEmployeesResponseDto.SimpleEmployeeDto dto = new DepartmentWithEmployeesResponseDto.SimpleEmployeeDto();
                dto.setId(employee.getId());
                dto.setName(employee.getName());
                dto.setRole(employee.getRole());
                dto.setJoiningDate(employee.getJoiningDate());
                if (employee.getReportingManager() != null) {
                    dto.setReportingManagerName(employee.getReportingManager().getName());
                } else {
                    dto.setReportingManagerName(null);
                }
                employeeDtos.add(dto);
            }

            response.setEmployees(employeeDtos);
        }

        return response;
    }

    public Page<EmployeeLookupDto> getEmployeeLookups(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAllLookup(pageable);
    }




}
