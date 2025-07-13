package com.example.Employee_Management_System.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head_id", nullable = true)
    private Employee departmentHead;


    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Employee> employees;
}

