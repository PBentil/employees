package com.example.AuthenticationSystem.HR.repository;

import com.example.AuthenticationSystem.HR.model.Employee;
import com.example.AuthenticationSystem.HR.model.SupportRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SupportRequestRepo extends JpaRepository<SupportRequest,Long> {
    Page<SupportRequest> findAll(Pageable pageable);
   @Query("SELECT h FROM SupportRequest h WHERE h.priority = :filter")
   Page<SupportRequest>filterPriorityRequests(@Param("filter")String filter,Pageable pageable);

   @Query("SELECT h FROM SupportRequest h WHERE h.uniqueId=:uniqueId")
   Page<SupportRequest> findMyRequest(@Param("uniqueId") String uniqueId,Pageable pageable);


}

