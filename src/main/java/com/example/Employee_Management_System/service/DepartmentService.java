package com.example.Employee_Management_System.service;


import com.example.Employee_Management_System.dto.DepartmentRequestDto;
import com.example.Employee_Management_System.dto.DepartmentResponseDto;
import com.example.Employee_Management_System.entity.Department;
import com.example.Employee_Management_System.exception.custom.DuplicateDepartmentException;
import com.example.Employee_Management_System.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

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
}
