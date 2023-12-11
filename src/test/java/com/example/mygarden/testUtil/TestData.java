package com.example.mygarden.testUtil;

import com.example.mygarden.model.entity.*;
import com.example.mygarden.model.enums.CategoryEnum;
import com.example.mygarden.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Component
public class TestData {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ShoppingBasketRepository shoppingBasketRepository;
    @Autowired
    private ShoppingItemRepository shoppingItemRepository;

    @Autowired
    private UserTestData userTestData;


    public Product createProduct(long id, String name, BigDecimal price, Set<Picture> pictureSet){
        Product testProduct = new Product();
        testProduct.setId(id);
        testProduct.setName(name);
        testProduct.setPrice(price);
        testProduct.setPictures(pictureSet);


        productRepository.save(testProduct);

        return testProduct;

    }
    public Picture createTestPic(long id, String title){
        Picture testPic = new Picture();
        testPic.setId(1L);
        testPic.setTitle(title);

        pictureRepository.save(testPic);

        return testPic;
    }

    public Category createTestCategory(long id, CategoryEnum name){
        Category testCategory = new Category();
        testCategory.setId(id);
        testCategory.setName(name);

        categoryRepository.save(testCategory);

        return testCategory;

    }
    public Order creatOrder(long id, User placedBy, List<ShoppingBasket> shoppingBasketList, boolean isPlaced){

        Order testOrder = new Order();
        testOrder.setId(id);
        testOrder.setShoppingBaskets(shoppingBasketList);
        testOrder.setPlacedBy(placedBy);
        testOrder.setPlaced(false);
        orderRepository.save(testOrder);

        return testOrder;

    }

    public ShoppingBasket creatShoppingBasket(long id, User buyer,
                                              Set<ShoppingItem> shoppingItemSet,BigDecimal totalSum, Order order){


        ShoppingBasket testBasket = new ShoppingBasket();
        testBasket.setId(id);
        testBasket.setBuyer(buyer);
        testBasket.setShoppingItems(shoppingItemSet);
        testBasket.setTotalSum(totalSum);
        testBasket.setOrder(order);
        shoppingBasketRepository.save(testBasket);

        return testBasket;

    }

    public ShoppingItem createShoppingItem(long id, String name, BigDecimal totalPrice, Product product){

        ShoppingItem testItem = new ShoppingItem();
        testItem.setId(id);
        testItem.setName(name);
        testItem.setTotalPrice(product.getPrice());
        testItem.setProduct(product);
        shoppingItemRepository.save(testItem);

        return testItem;

    }


    public void cleanAllTestData(){

        productRepository.deleteAll();
        orderRepository.deleteAll();
        shoppingBasketRepository.deleteAll();
        shoppingItemRepository.deleteAll();
    }
}
