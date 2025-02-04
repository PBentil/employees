package com.example.AuthenticationSystem.HR.repository;
import com.example.AuthenticationSystem.HR.model.FandC;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbacksAndComplaintsRepo extends JpaRepository<FandC,Long> {
}
