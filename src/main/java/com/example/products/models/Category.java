package com.example.products.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Category extends BaseModel{
    @OneToMany(mappedBy = "category" ,fetch = FetchType.EAGER)
    List<Product> products;
}
