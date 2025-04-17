package com.example.AuthenticationSystem.HR.model;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class Announcement {






    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String title;

        @Column(nullable = false, columnDefinition = "TEXT")
        private String content;

        @Column(nullable = false)
        private String contentType;

        @Column(nullable = false)
        private LocalDate date;


        public Announcement() {}

        public Announcement(String title, String content, String contentType, LocalDate date) {
            this.title = title;
            this.content = content;
            this.contentType = contentType;
            this.date= date;
        }


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate Date) {
            this.date = date;
        }
    }


