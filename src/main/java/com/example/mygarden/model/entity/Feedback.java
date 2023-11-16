package com.example.mygarden.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "feedbacks")
public class Feedback extends BaseEntity{
    private String content;
    @ManyToOne
    private User writtenBy;


    public Feedback() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(User writtenBy) {
        this.writtenBy = writtenBy;
    }


}
