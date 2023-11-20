package com.example.mygarden.web;

import com.example.mygarden.model.dto.ProductViewDto;
import com.example.mygarden.service.ProductService;
import com.example.mygarden.service.impl.ProductServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsAllController {

   private final ProductService productService;

    public ProductsAllController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public String all(Model model){

        List<ProductViewDto> allProducts = productService.findAll();
        model.addAttribute("products", allProducts);




        return "products";
    }

}
