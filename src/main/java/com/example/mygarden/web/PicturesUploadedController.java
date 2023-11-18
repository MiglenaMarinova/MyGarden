package com.example.mygarden.web;

import com.example.mygarden.model.dto.PictureViewDto;
import com.example.mygarden.service.PictureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pictures")
public class PicturesUploadedController {

    private final PictureService pictureService;

    public PicturesUploadedController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @GetMapping("/all")
    public String allUploadedPic(Model model){
        List<PictureViewDto> pictures = pictureService.findAll();
        model.addAttribute("pictures", pictures);

        return "uploaded-pictures";
    }

}
