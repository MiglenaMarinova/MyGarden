package com.example.mygarden.service;

import com.example.mygarden.model.dto.PictureAddDto;
import com.example.mygarden.model.entity.Picture;
import com.example.mygarden.repository.PictureRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PictureService {

    private final PictureRepository pictureRepository;

    private final ModelMapper modelMapper;


    public PictureService(PictureRepository pictureRepository, ModelMapper modelMapper) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
    }

    public void addPicture(PictureAddDto pictureAddDto) {

        Picture picture = modelMapper.map(pictureAddDto, Picture.class);
        pictureRepository.save(picture);
    }
}
