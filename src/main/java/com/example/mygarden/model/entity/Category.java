package com.example.mygarden.model.entity;

import com.example.mygarden.model.enums.CategoryEnum;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{

    private String description;
    @Enumerated(EnumType.STRING)
    private CategoryEnum name;
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Product> products;

    public Category() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryEnum getName() {
        return name;
    }

    public void setName(CategoryEnum name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
