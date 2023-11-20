package com.example.mygarden.web;

import com.example.mygarden.model.dto.PictureViewDto;
import com.example.mygarden.service.impl.PictureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pictures")
public class GalleryController {

    private final PictureService pictureService;

    public GalleryController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @GetMapping("/all")
    public String allUploadedPic(Model model){

        List<PictureViewDto> pictures = pictureService.findAll();
        model.addAttribute("pictures", pictures);

        return "gallery";
    }

}
