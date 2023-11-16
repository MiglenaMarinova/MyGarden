package com.example.mygarden.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PictureAddDto {
    @NotNull
    @Size(min = 3, max = 10, message = "Title must be between 3 and 10")
    private String title;
    @NotEmpty(message = "Missing url")
    private String url;

    public PictureAddDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
