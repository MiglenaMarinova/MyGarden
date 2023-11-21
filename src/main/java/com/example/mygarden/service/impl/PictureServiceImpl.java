package com.example.mygarden.service.impl;

import com.example.mygarden.model.dto.PictureViewDto;
import com.example.mygarden.model.entity.Picture;
import com.example.mygarden.model.serviceModel.PictureServiceModel;
import com.example.mygarden.repository.PictureRepository;
import com.example.mygarden.service.CloudinaryService;
import com.example.mygarden.service.exeption.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements com.example.mygarden.service.PictureService {

    private final PictureRepository pictureRepository;

    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
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
        List<Picture> allPic = pictureRepository.findAll();
        List<PictureViewDto> allPicViewDto = new ArrayList<>();
        if (!allPic.isEmpty()){
             allPicViewDto=allPic
                    .stream()
                    .map(picture -> modelMapper.map(picture, PictureViewDto.class))
                    .collect(Collectors.toList());
        }
        return allPicViewDto;
//        return pictureRepository
//                .findAll()
//                .stream()
//                .map(picture -> modelMapper.map(picture, PictureViewDto.class))
//                .collect(Collectors.toList());
    }

    public List<Picture> findAllByTittle(String name) {

        return pictureRepository.findAllByTitle(name)
                .orElseThrow(()-> new ObjectNotFoundException("Available pictures not found"));
    }
}
