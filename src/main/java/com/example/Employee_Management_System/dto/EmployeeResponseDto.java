package com.example.Employee_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDto {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private Double salary;
    private String address;
    private String role;
    private LocalDate joiningDate;
    private Integer yearlyBonusPercentage;
    private Long departmentId;
    private String departmentName;
    private Long reportingManagerId;
    private String reportingManagerName;
}
