package com.example.AuthenticationSystem.HR.repository;

import com.example.AuthenticationSystem.HR.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<Task, Long> {

     List<Task> findAllByUniqueId(String uniqueId);

}
