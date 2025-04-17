package com.example.AuthenticationSystem.HR.repository;

import com.example.AuthenticationSystem.HR.model.Tasks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<Tasks,Long> {

     Page<Tasks> findAllByUniqueId(String uniqueId, Pageable pageable);
<<<<<<< HEAD
=======
     Tasks findById(long id);

     @Query("SELECT t FROM Tasks t WHERE  t.uniqueId=:uniqueId AND t.id=:id")
     Tasks findByUniqueIdAndId(@Param ("uniqueId")String uniqueId,@Param("id") long id);

     @Query("SELECT t From Tasks t WHERE t.id=:id AND t.status=:status")
     Optional<Tasks> findByReportStatus(@Param("id") Long id,@Param("status") String status);

     @Query("SELECT COUNT(t) FROM Tasks t WHERE t.uniqueId=:uniqueId AND t.status=:status")
     Long findTaskStatus(@Param("uniqueId")String uniqueId,@Param("status") String status);

     @Query("SELECT COUNT(t) FROM Tasks t WHERE t.status=:status")
     Long findTaskStatusCount(@Param("status") String status);
>>>>>>> origin/main

}
