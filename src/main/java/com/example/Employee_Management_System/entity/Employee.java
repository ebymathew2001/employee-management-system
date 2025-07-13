package com.example.Employee_Management_System.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    
    @Column(nullable = false)
    private Double salary;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private String role;
    
    @Column(name = "joining_date", nullable = false)
    private LocalDate joiningDate;
    
    @Column(name = "yearly_bonus_percentage", nullable = false)
    private Integer yearlyBonusPercentage;
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporting_manager_id", nullable = true)
    private Employee reportingManager;


}