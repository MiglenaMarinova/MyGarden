package com.example.mygarden.service;

import com.example.mygarden.model.dto.PictureViewDto;
import com.example.mygarden.model.entity.Picture;
import com.example.mygarden.model.serviceModel.PictureServiceModel;
import com.example.mygarden.repository.PictureRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PictureService {

    private final PictureRepository pictureRepository;

    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;


    public PictureService(PictureRepository pictureRepository, ModelMapper modelMapper,
                          CloudinaryService cloudinaryService) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }


    public void addPicture(PictureServiceModel pictureServiceModel) throws IOException {

        MultipartFile img = pictureServiceModel.getImg();
        String imageUrl = cloudinaryService.uploadImage(img);

        Picture picture = new Picture();

        picture.setTitle(pictureServiceModel.getTitle());
        picture.setImgUrl(imageUrl);

        pictureRepository.save(picture);
    }

    public List<PictureViewDto> findAll() {

        return pictureRepository
                .findAll()
                .stream()
                .map(picture -> modelMapper.map(picture, PictureViewDto.class))
                .collect(Collectors.toList());
    }
}
