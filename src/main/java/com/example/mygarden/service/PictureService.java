package com.example.mygarden.service;

import com.example.mygarden.model.dto.PictureViewDto;
import com.example.mygarden.model.entity.Picture;
import com.example.mygarden.model.serviceModel.PictureServiceModel;

import java.io.IOException;
import java.util.List;

public interface PictureService {

    void addPicture(PictureServiceModel pictureServiceModel) throws IOException;

    List<PictureViewDto> findAll();

    List<Picture> findAllByTittle(String name);
}
