package com.example.mygarden.model.dto;

public class PictureViewDto {

    private Long id;
    private String title;
    private String imgUrl;

    private Integer amount;

    public PictureViewDto() {
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getAmount() {
        return amount;
    }

    public PictureViewDto setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }
}
