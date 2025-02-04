package com.example.AuthenticationSystem.HR.repository;

import com.example.AuthenticationSystem.HR.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LeaveRepo extends JpaRepository<Leave, Long> {
}
