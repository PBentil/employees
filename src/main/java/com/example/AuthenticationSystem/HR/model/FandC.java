package com.example.AuthenticationSystem.HR.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
@Entity
public class FandC {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String uniqueId;
    private String feedbackType;
    private String feedbackDesc;
    private String status;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getFeedbackDesc() {
        return feedbackDesc;
    }

    public void setFeedbackDesc(String feedbackDesc) {
        this.feedbackDesc = feedbackDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
