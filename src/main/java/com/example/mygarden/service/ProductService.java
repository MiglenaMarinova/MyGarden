package com.example.mygarden.service;

import com.example.mygarden.model.dto.PictureViewDto;
import com.example.mygarden.model.dto.ProductAddDto;
import com.example.mygarden.model.dto.ProductViewDto;
import com.example.mygarden.model.entity.Category;
import com.example.mygarden.model.entity.Picture;
import com.example.mygarden.model.entity.Product;
import com.example.mygarden.model.enums.CategoryEnum;
import com.example.mygarden.repository.CategoryRepository;
import com.example.mygarden.repository.ProductRepository;
import com.example.mygarden.service.exeption.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final PictureService pictureService;


    public ProductService(ModelMapper modelMapper, ProductRepository productRepository,
                          CategoryRepository categoryRepository, PictureService pictureService) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;

        this.pictureService = pictureService;
    }

    public void addProduct(ProductAddDto productAddDto) {


        Product product = modelMapper.map(productAddDto, Product.class);
        Category category = categoryRepository.findByName(productAddDto.getCategory())
                .orElseThrow(() -> new ObjectNotFoundException("Category not found"));
        if (category != null) {
            product.setCategory(category);
        }

        productRepository.save(product);
    }


    public List<ProductViewDto> findAll() {
//        return productRepository.findAll()
//                .stream()
//                .map(product -> modelMapper
//                        .map(product, ProductViewDto.class))
//                .collect(Collectors.toList());

        List<ProductViewDto> all = productRepository.findAll()
                .stream()
                .map(product -> {
                    ProductViewDto productViewDto = modelMapper.map(product, ProductViewDto.class);
                    List<PictureViewDto> pictureViewDtos =
                    product.getPictures()
                            .stream()
                            .map(picture -> modelMapper.map(picture, PictureViewDto.class))
                            .collect(Collectors.toList());

                    productViewDto.setPictureViewList(pictureViewDtos);

                    return productViewDto;
                }).collect(Collectors.toList());


        return all;
    }

    public List<ProductViewDto> findByCategoryName(CategoryEnum categoryEnum) {
        return productRepository.findAllByCategory_Name(categoryEnum)
                .stream()
                .map(product -> modelMapper
                        .map(product, ProductViewDto.class))
                .collect(Collectors.toList());

    }

    public List<ProductViewDto> findByCategoryOther(CategoryEnum categoryEnum) {

        return productRepository.findAllByCategory_Name(categoryEnum)
                .stream()
                .map(product -> modelMapper
                        .map(product, ProductViewDto.class))
                .collect(Collectors.toList());
    }


    public void changePic(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Product not found"));

        List<Picture> availablePic = pictureService.findAllByTittle(product.getName());

        if(!availablePic.isEmpty()){
          for (Picture picture : availablePic){
              product.getPictures().add(picture);
              picture.setProduct(product);
          }

        }
        
        productRepository.save(product);
    }
}
