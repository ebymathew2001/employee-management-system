package com.example.Employee_Management_System.controller;


import com.example.Employee_Management_System.dto.DepartmentRequestDto;
import com.example.Employee_Management_System.dto.DepartmentResponseDto;
import com.example.Employee_Management_System.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
