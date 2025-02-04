package com.example.AuthenticationSystem.HR.repository;

import com.example.AuthenticationSystem.HR.model.JobDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JobRepo extends JpaRepository<JobDetails, Long> {

}
