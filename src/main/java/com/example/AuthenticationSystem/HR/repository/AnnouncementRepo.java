<<<<<<< HEAD
package com.example.AuthenticationSystem.HR.repository;

import com.example.AuthenticationSystem.HR.model.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnnouncementRepo extends JpaRepository<Announcement,Long> {
    Page<Announcement> findAll(Pageable pageable);
}
=======
package com.example.AuthenticationSystem.HR.repository;

import com.example.AuthenticationSystem.HR.model.Announcement;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface AnnouncementRepo extends JpaRepository<Announcement,Long> {
    Page<Announcement> findAll(Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM  Announcement a WHERE a.id=:id")
    void deleteAnnouncement(@Param("id") long id);
    LocalDate today=LocalDate.now();


    @Query("SELECT COUNT(a) FROM Announcement a WHERE a.date = :today")
    Long countTodayAnnouncements(@Param("today") LocalDate today);
}
>>>>>>> origin/main
