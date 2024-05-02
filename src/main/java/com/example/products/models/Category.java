package com.example.products.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Category extends BaseModel{
    @OneToMany(mappedBy = "category" ,fetch = FetchType.EAGER)
    List<Product> products;
}
