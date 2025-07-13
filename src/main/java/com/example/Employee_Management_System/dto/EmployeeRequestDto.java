package com.example.Employee_Management_System.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be positive")
    @DecimalMax(value = "99999999.99", message = "Salary cannot exceed 99999999.99")
    private Double salary;

    @NotBlank(message = "Address is required")
    @Size(max = 500, message = "Address cannot exceed 500 characters")
    private String address;

    @NotBlank(message = "Role is required")
    @Size(max = 50, message = "Role cannot exceed 100 characters")
    private String role;

    @NotNull(message = "Joining date is required")
    @PastOrPresent(message = "Joining date cannot be in the future")
    private LocalDate joiningDate;

    @NotNull(message = "Bonus percentage is required")
    @Min(value = 0, message = "Bonus percentage must be at least 0")
    @Max(value = 100, message = "Bonus percentage cannot exceed 100")
    private Integer bonusPercentage;

    @NotNull(message = "Department ID is required")
    @Positive(message = "Department ID must be a positive number")
    private Long departmentId;


    @Positive(message = "Manager ID must be a positive number")
    private Long managerId;
}
