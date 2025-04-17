package com.example.AuthenticationSystem.HR.repository;


import com.example.AuthenticationSystem.HR.model.Attendance;
import com.example.AuthenticationSystem.HR.model.Leave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface EmployeeAttendance  extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByUniqueIdAndDate(String uniqueId, LocalDate date);
    List<Attendance> findByDate(LocalDate date);
    Optional<Attendance> findByUniqueId(String uniqueId);
    List<Attendance> findAllByUniqueId(String uniqueId);
    long countByDayOfWeek(DayOfWeek dayOfWeek);
    @Query("SELECT a FROM Attendance a WHERE a.timeliness = :timeliness")
    Page<Attendance> findfilteredRecords(@Param("timeliness") String timeliness, Pageable pageable);
<<<<<<< HEAD
=======
    @Query("SELECT a FROM Attendance a WHERE a.uniqueId=:uniqueId")
    Page<Attendance> findEmployeeAttendance(@Param("uniqueId")String uniqueId,Pageable pageable);
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.date=:date")
    Long totalLogin(@Param("date")LocalDate date);
>>>>>>> origin/main
}
