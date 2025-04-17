package com.example.AuthenticationSystem.HR.repository;

import com.example.AuthenticationSystem.HR.model.Compensation;
import com.example.AuthenticationSystem.HR.model.JobDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface JobRepo extends JpaRepository<JobDetails, Long> {
    Optional<JobDetails> findByUniqueId(String uniqueId);
}
