package com.example.mygarden.web;

import com.example.mygarden.service.PictureService;
import com.example.mygarden.model.serviceModel.PictureServiceModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/pictures")
public class PicturesController {

    private final PictureService pictureService;

    public PicturesController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @GetMapping("/add")
    public String add(){
        return "picture_add";
    }

    @PostMapping("/add")
    public String addPicture(@ModelAttribute("pictureServiceModel")
                                 PictureServiceModel pictureServiceModel) throws IOException {


        this.pictureService.addPicture(pictureServiceModel);

        return "redirect:/";
    }





}
