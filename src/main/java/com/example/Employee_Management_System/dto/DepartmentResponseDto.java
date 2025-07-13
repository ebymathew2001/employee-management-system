package com.example.Employee_Management_System.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponseDto {

    private Long id;

    private String name;

    private LocalDate creationDate;

    private Long departmentHeadId;
}
