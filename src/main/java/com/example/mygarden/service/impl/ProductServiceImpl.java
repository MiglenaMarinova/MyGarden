package com.example.mygarden.service.impl;

import com.example.mygarden.model.dto.PictureViewDto;
import com.example.mygarden.model.dto.ProductAddDto;
import com.example.mygarden.model.dto.ProductViewDto;
import com.example.mygarden.model.entity.*;
import com.example.mygarden.repository.CategoryRepository;
import com.example.mygarden.repository.ProductRepository;
import com.example.mygarden.service.OrderService;
import com.example.mygarden.service.PictureService;
import com.example.mygarden.service.UserService;
import com.example.mygarden.service.exeption.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements com.example.mygarden.service.ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final PictureService pictureService;
    private final UserService userService;
    private final OrderService orderService;



    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository,
                              CategoryRepository categoryRepository, PictureService pictureService,
                              UserService userService, OrderService orderService) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.pictureService = pictureService;
        this.userService = userService;
        this.orderService = orderService;
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

    @Override
    public void buy(Long id, UserDetails buyer) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Product not available."));
        User userBuyer = userService.findByEmail(buyer.getUsername());
        Order order = orderService.findByUser(userBuyer.getId());

        if (order != null && !order.isPlaced()) {
            product.setOrder(order);
            productRepository.save(product);
            order.getOrderedProducts().add(product);
            orderService.save(order);
        }else if (order == null){
            Order newOrder = new Order();
            List<Product> products = new ArrayList<>();
            products.add(product);
            newOrder.setOrderedProducts(products);
            newOrder.setPlacedBy(userBuyer);
            orderService.save(newOrder);
            product.setOrder(newOrder);
            productRepository.save(product);
            userBuyer.getOrders().add(newOrder);
            userService.save(userBuyer);
        }

    }

    @Override
    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);

    }

    @Override
    public ProductViewDto findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ObjectNotFoundException("Product not found"));

        return modelMapper.map(product, ProductViewDto.class);
    }

    @Override
    @Transactional
    public void update(ProductViewDto productToUpdate) {
        Product product = modelMapper.map(productToUpdate, Product.class);
        productRepository.save(product);
    }

    @Override
    public Product findProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Not found"));
    }

    @Override
    public void saveChanges(Product existingProduct) {
        productRepository.save(existingProduct);
    }


}
