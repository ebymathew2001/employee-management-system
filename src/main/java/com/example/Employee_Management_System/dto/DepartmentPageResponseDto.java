package com.example.Employee_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentPageResponseDto {
    private List<DepartmentResponseDto> departments;
    private int currentPage;
    private int totalPages;
    private long totalItems;
}
