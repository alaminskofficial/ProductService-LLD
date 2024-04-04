package com.example.products.service;

import com.example.products.dtos.ProductRequestDtoFake;
import com.example.products.exceptions.ProductNotPresentException;
import com.example.products.models.Category;
import com.example.products.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();

    Product getSingleProduct(Long id) throws ProductNotPresentException;;

    List<Category> getAllPCategories();
    List<Product> getProductsByCategory(String id);
    Product addProduct(ProductRequestDtoFake product);
}
