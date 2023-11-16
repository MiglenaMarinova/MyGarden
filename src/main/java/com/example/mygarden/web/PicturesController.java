package com.example.mygarden.web;

import com.example.mygarden.model.dto.PictureAddDto;
import com.example.mygarden.service.PictureService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String pictureAdd(@Valid PictureAddDto pictureAddDto, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes
                    .addFlashAttribute("pictureAddDto", pictureAddDto);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.pictureAddDto", bindingResult);

            return "redirect:add";
        }

        pictureService.addPicture(pictureAddDto);


        return "redirect:/";
    }

    @ModelAttribute
    public PictureAddDto pictureAddDto(){
        return new PictureAddDto();
    }
}
