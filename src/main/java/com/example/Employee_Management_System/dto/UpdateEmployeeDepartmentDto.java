package com.example.Employee_Management_System.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeDepartmentDto {
    @NotNull(message = "Department ID is required")
    @Positive(message = "Department ID must be a positive number")
    private Long departmentId;
}
