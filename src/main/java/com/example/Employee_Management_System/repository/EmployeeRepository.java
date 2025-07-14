package com.example.Employee_Management_System.repository;

import com.example.Employee_Management_System.dto.EmployeeLookupDto;
import com.example.Employee_Management_System.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByNameAndDateOfBirthAndDepartmentId(String name, LocalDate dateOfBirth, Long departmentId);

    boolean existsByNameAndDateOfBirthAndDepartmentIdAndIdNot(String name, LocalDate dateOfBirth, Long departmentId, Long id);

    long countByDepartmentId(Long departmentId);

    @Query("SELECT new com.example.Employee_Management_System.dto.EmployeeLookupDto(e.id, e.name) FROM Employee e")
    Page<EmployeeLookupDto> findAllLookup(Pageable pageable);



}
