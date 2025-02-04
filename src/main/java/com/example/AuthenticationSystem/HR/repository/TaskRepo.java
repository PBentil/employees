package com.example.AuthenticationSystem.HR.repository;

import com.example.AuthenticationSystem.HR.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<Tasks,Long> {

     List<Tasks> findAllByUniqueId(String uniqueId);

}
