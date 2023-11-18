package com.example.mygarden.web;

import com.example.mygarden.model.dto.ProductViewDto;
import com.example.mygarden.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("moderator")
public class ModeratorController {

    private final ProductService productService;

    public ModeratorController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/manage")
    public String managePic(Model model){


    List<ProductViewDto> allProducts = productService.findAll();
    model.addAttribute("allProducts", allProducts);


        return "moderator-page";
    }




}


