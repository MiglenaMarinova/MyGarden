package com.example.mygarden.model.serviceModel;

import org.springframework.web.multipart.MultipartFile;

public class PictureServiceModel {

    private Long id;
    private String title;
    private MultipartFile img;

    public PictureServiceModel() {
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

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }
}
