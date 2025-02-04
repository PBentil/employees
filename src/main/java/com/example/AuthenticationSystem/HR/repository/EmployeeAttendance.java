package com.example.AuthenticationSystem.HR.repository;


import com.example.AuthenticationSystem.HR.model.Attendance;
import com.example.AuthenticationSystem.HR.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface EmployeeAttendance  extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByUniqueIdAndDate(String uniqueId, LocalDate date);

    Optional<Attendance> findByUniqueId(String uniqueId);
    List<Attendance> findAllByUniqueId(String uniqueId);
}
