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



}
