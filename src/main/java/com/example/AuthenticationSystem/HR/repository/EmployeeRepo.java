package com.example.AuthenticationSystem.HR.repository;

import com.example.AuthenticationSystem.HR.model.Employee;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Page<Employee> findAll(Pageable pageable);
    Employee findByEmail(String email);
    Employee findByUniqueId(String uniqueId);
}
