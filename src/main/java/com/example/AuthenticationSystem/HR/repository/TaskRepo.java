package com.example.AuthenticationSystem.HR.repository;

import com.example.AuthenticationSystem.HR.model.Tasks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<Tasks,Long> {

     Page<Tasks> findAllByUniqueId(String uniqueId, Pageable pageable);

}
