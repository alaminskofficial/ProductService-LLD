package com.example.products.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Category extends BaseModel{
    @OneToMany(mappedBy = "category")
    List<Product> products;
}
