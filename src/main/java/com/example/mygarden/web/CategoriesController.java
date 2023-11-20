package com.example.mygarden.web;

import com.example.mygarden.model.dto.CategoryPicDto;
import com.example.mygarden.model.enums.CategoryEnum;
import com.example.mygarden.service.impl.CategoryService;
import com.example.mygarden.service.impl.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoriesController {
    private final ProductService productService;
    private final CategoryService categoryService;



    public CategoriesController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }



    @GetMapping("/veggies")
    public String getVeggies(Model model){
        List<CategoryPicDto> allCat = categoryService.findAll();
        CategoryPicDto categoryPicDto = categoryService.findByNameVeg(CategoryEnum.VEGETABLE);

        model.addAttribute("allCat", allCat);
        model.addAttribute("allVeggies", categoryPicDto.getProductViewDtoList());

//
//        List<ProductViewDto> allVeggies = productService.findByCategoryName(CategoryEnum.valueOf("VEGETABLE"));
//        model.addAttribute("allVeggies", allVeggies);

        return "categories";
    }
    @GetMapping("/others")
    public String getOther(Model model){
        List<CategoryPicDto> allCat = categoryService.findAll();
        CategoryPicDto categoryPicDto = categoryService.findByNameOther(CategoryEnum.OTHER);

        model.addAttribute("allCat", allCat);
        model.addAttribute("allOther", categoryPicDto.getProductViewDtoList());

        return "categories";
    }
    @GetMapping("/spices")
    public String getSpices(Model model){
        List<CategoryPicDto> allCat = categoryService.findAll();
        CategoryPicDto categoryPicDto = categoryService.findByNameSpices(CategoryEnum.SPICE);

        model.addAttribute("allCat", allCat);
        model.addAttribute("allSpices", categoryPicDto.getProductViewDtoList());

        return "categories";
    }
    @GetMapping("/homemades")
    public String getHomemade(Model model){
        List<CategoryPicDto> allCat = categoryService.findAll();
        CategoryPicDto categoryPicDto = categoryService.findByNameHomemade(CategoryEnum.HOMEMADE);

        model.addAttribute("allCat", allCat);
        model.addAttribute("allHomemade", categoryPicDto.getProductViewDtoList());

        return "categories";
    }






}
