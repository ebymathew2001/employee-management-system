package com.example.Employee_Management_System.repository;

import com.example.Employee_Management_System.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    boolean existsByName(String name);
    Optional<Department> findByName(String name);
}
