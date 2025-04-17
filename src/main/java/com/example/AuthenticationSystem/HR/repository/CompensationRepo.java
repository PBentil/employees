package com.example.AuthenticationSystem.HR.repository;

import com.example.AuthenticationSystem.HR.model.Compensation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompensationRepo extends JpaRepository<Compensation, Long> {
}

