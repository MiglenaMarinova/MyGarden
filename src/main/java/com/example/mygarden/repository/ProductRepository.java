package com.example.mygarden.repository;

import com.example.mygarden.model.entity.Product;
import com.example.mygarden.model.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
@Query("SELECT p FROM Product p " +
        "WHERE p.category.name = :category_name")
List<Product> findAllByCategory_Name(CategoryEnum category_name);
}
