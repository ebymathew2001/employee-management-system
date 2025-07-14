package com.example.Employee_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePageResponseDto {

    private List<EmployeeResponseDto> employees;
    private int currentPage;
    private int totalPages;
    private long totalItems;
}
