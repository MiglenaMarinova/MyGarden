package com.example.mygarden.web;

import com.example.mygarden.model.dto.PictureViewDto;
import com.example.mygarden.model.dto.ProductViewDto;
import com.example.mygarden.service.impl.PictureService;
import com.example.mygarden.service.impl.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("moderator")
public class ModeratorController {

    private final ProductService productService;
    private final PictureService pictureService;


    public ModeratorController(ProductService productService, PictureService pictureService) {
        this.productService = productService;
        this.pictureService = pictureService;
    }


    @GetMapping("/manage")
    public String managePic(Model model){


    List<ProductViewDto> allProducts = productService.findAll();
    model.addAttribute("allProducts", allProducts);
    List<PictureViewDto> allAvailablePic = pictureService.findAll();
    model.addAttribute("allAvailablePic", allAvailablePic);

        return "moderator-page";
    }





}


