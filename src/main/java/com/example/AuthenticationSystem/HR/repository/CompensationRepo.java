package com.example.AuthenticationSystem.HR.repository;

import com.example.AuthenticationSystem.HR.model.Compensation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CompensationRepo extends JpaRepository<Compensation, Long> {

    Optional<Compensation>findByUniqueId(String uniqueId);
}

