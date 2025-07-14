package com.example.Employee_Management_System.controller;

import com.example.Employee_Management_System.dto.*;
import com.example.Employee_Management_System.entity.Employee;
import com.example.Employee_Management_System.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(@Valid @RequestBody EmployeeRequestDto requestDto) {
        EmployeeResponseDto responseDto = employeeService.createEmployee(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDto dto) {
        EmployeeResponseDto responseDto = employeeService.updateEmployee(id, dto);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}/department")
    public ResponseEntity<EmployeeResponseDto> updateEmployeeDepartment(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEmployeeDepartmentDto dto) {
        EmployeeResponseDto response = employeeService.updateEmployeeDepartment(id, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getEmployees(
            @RequestParam(required = false) Boolean lookup,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        if (Boolean.TRUE.equals(lookup)) {
            return ResponseEntity.ok(employeeService.getEmployeeLookups(page, size));
        } else {
            return ResponseEntity.ok(employeeService.getAllEmployees(page, size));
        }
    }



}
