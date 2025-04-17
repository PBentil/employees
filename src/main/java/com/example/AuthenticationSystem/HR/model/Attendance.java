package com.example.AuthenticationSystem.HR.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.*;

@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String uniqueId;
    private LocalDate date;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private String status;
    private String timeliness; // ✅ Fixed variable name

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;



    public void setDate(LocalDate date) {
        this.date = date;
        this.dayOfWeek = date.getDayOfWeek();
    }

    public LocalDate getDate() {
        return date;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public LocalTime getCheckInTime() {
        return checkInTime;
    }
    public void setCheckInTime(LocalTime checkInTime) {
        this.checkInTime = checkInTime.truncatedTo(java.time.temporal.ChronoUnit.MINUTES);
    }


    public LocalTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeliness() {
        return timeliness;
    }

    public void setTimeliness(String timeliness) {
        this.timeliness = timeliness;
    }
}
