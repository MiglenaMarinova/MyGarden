package com.example.mygarden.web;

import com.example.mygarden.model.dto.ProductAddDto;
import com.example.mygarden.model.dto.ProductViewDto;
import com.example.mygarden.model.entity.Product;
import com.example.mygarden.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;



    public ProductController(ProductService productService) {
        this.productService = productService;

    }

    @GetMapping("/add")
    public String add(){
        return "product-add";
    }

    @PostMapping("/add")
    public String addProduct(@Valid ProductAddDto productAddDto, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            redirectAttributes
                    .addFlashAttribute("productAddDto", productAddDto);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.productAddDto", bindingResult);

            return "redirect:add";
        }

        productService.addProduct(productAddDto);

        return "redirect:/products/add";

    }

    @ModelAttribute
    public ProductAddDto productAddDto(){
        return new ProductAddDto();
    }


    @GetMapping("change-pic/{id}")
    public String changePic(@PathVariable Long id){

        this.productService.changePic(id);
        return "redirect:/moderator/manage";
    }
    @GetMapping("buy/{id}")
    public String buy(@PathVariable Long id,
                      @AuthenticationPrincipal UserDetails buyer){

        this.productService.buy(id, buyer);

        return "redirect:/user/orders";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){

        this.productService.delete(id);

        return "redirect:/products/all";
    }

    @GetMapping("/change-price/{id}")
    public String changePrice(@PathVariable Long id, Model model){

        model.addAttribute("product", productService.findById(id));

        return "update-product";
    }


    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateProduct(@PathVariable Long id,

                                @ModelAttribute("product")ProductViewDto productViewDto,
                                Model model){
//        model.addAttribute("product", productViewDto);
        Product existingProduct = productService.findProduct(id);
        existingProduct.setId(productViewDto.getId());
        existingProduct.setPrice(productViewDto.getPrice());
        productService.saveChanges(existingProduct);

        return "redirect:/products/all";
    }









}
