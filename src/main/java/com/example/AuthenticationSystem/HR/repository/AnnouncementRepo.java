package com.example.AuthenticationSystem.HR.repository;

import com.example.AuthenticationSystem.HR.model.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnnouncementRepo extends JpaRepository<Announcement,Long> {
    Page<Announcement> findAll(Pageable pageable);
}
