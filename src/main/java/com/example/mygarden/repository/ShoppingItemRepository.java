package com.example.mygarden.repository;

import com.example.mygarden.model.entity.Product;
import com.example.mygarden.model.entity.ShoppingBasket;
import com.example.mygarden.model.entity.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {

    @Query("SELECT i FROM ShoppingItem i " +
            "WHERE i.product.id = :product_id AND i.shoppingBasket.id = :shoppingBasket_id")
    Optional<ShoppingItem> findShoppingItemByProduct_IdAndShoppingBasket_Id(Long product_id, Long shoppingBasket_id);


}
