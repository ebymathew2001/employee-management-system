package com.example.Employee_Management_System.controller;

import com.example.Employee_Management_System.dto.EmployeeRequestDto;
import com.example.Employee_Management_System.entity.Employee;
import com.example.Employee_Management_System.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeRequestDto requestDto) {
        Employee employee = employeeService.createEmployee(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }
}
