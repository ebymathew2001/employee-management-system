package com.example.Employee_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentWithEmployeesResponseDto {

    private Long id;
    private String name;
    private LocalDate creationDate;
    private List<SimpleEmployeeDto> employees;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SimpleEmployeeDto {
        private Long id;
        private String name;
        private String role;
        private LocalDate joiningDate;
        private String reportingManagerName;
    }
}
