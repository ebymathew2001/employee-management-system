package com.example.Employee_Management_System.controller;


import com.example.Employee_Management_System.dto.*;
import com.example.Employee_Management_System.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentResponseDto> createDepartment(
            @Valid @RequestBody DepartmentRequestDto requestDto) {

        DepartmentResponseDto responseDto = departmentService.createDepartment(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentUpdateRequestDto requestDto) {

        DepartmentResponseDto updated = departmentService.updateDepartment(id, requestDto);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok("Department deleted successfully");
    }

    @GetMapping
    public ResponseEntity<DepartmentPageResponseDto> getAllDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        DepartmentPageResponseDto response = departmentService.getAllDepartments(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentWithEmployeesResponseDto> getDepartmentById(
            @PathVariable Long id,
            @RequestParam(name = "expand", required = false) String expandParam) {

        boolean expandEmployees = "employee".equalsIgnoreCase(expandParam);

        DepartmentWithEmployeesResponseDto response = departmentService.getDepartmentById(id, expandEmployees);
        return ResponseEntity.ok(response);
    }







}
