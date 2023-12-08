package com.example.mygarden.repository;

import com.example.mygarden.model.entity.ShoppingBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingBasketRepository extends JpaRepository<ShoppingBasket, Long> {

    @Query("SELECT b from ShoppingBasket b " +
            "WHERE b.buyer.id = :id")
    Optional<ShoppingBasket> findByUser(Long id);

    @Query("SELECT b FROM ShoppingBasket  b " +
            "WHERE b.order.id = :id")
    Optional<ShoppingBasket> findByOrder(Long id);
}
