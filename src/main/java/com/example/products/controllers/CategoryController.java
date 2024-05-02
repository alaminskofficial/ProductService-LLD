package com.example.products.controllers;

import com.example.products.models.Category;

import com.example.products.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/category")
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }
    @GetMapping("/category/{id}")
    public Category getSingleCategory(@PathVariable("id")Long id){
        return categoryRepository.findById(id).get();
    }

}
