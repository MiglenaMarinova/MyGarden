package com.example.mygarden.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity{

    private String title;
    private String imgUrl;



    public Picture() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
