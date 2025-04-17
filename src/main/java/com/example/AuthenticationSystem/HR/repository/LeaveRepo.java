package com.example.AuthenticationSystem.HR.repository;

import com.example.AuthenticationSystem.HR.model.Attendance;
import com.example.AuthenticationSystem.HR.model.Leave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LeaveRepo extends JpaRepository<Leave, Long> {
    List<Leave> findByUniqueId(String uniqueId);
    @Query("SELECT l FROM Leave l WHERE  l.uniqueId=:uniqueId")
    Page<Leave> findBySpecific(@Param("uniqueId") String uniqueId, Pageable pageable);

    List<Leave> findAllByStatus(String approved);
    Page<Leave> findByStatus(String status, Pageable pageable);
    @Query("SELECT l FROM Leave l WHERE l.status = :status")
    Page<Leave> findfilteredRecords(@Param("status") String status, Pageable pageable);

    @Query("SELECT l FROM Leave l WHERE  l.uniqueId=:uniqueId AND l.status = :status")
    Page<Leave> findEmployeeFilteredRecords(@Param("status") String status,@Param("uniqueId") String uniqueId, Pageable pageable);



}
