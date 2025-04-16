package com.example.AuthenticationSystem.HR.repository;

import com.example.AuthenticationSystem.HR.model.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Page<Employee> findAll(Pageable pageable);
    Employee findByEmail(String email);
    Employee findByUniqueId(String uniqueId);
    @Transactional
    @Modifying
    @Query("DELETE FROM Employee e WHERE e.uniqueId = :uniqueId")
    void deleteByUniqueId(String uniqueId);




}
