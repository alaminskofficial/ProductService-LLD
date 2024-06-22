package com.example.products.repositories;

import com.example.products.models.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository<PageAble> extends JpaRepository<Product, Long> {
    Product findByDescription(String description);
    Product findByName(String name);
    Product findByPrice(Float price);

    List<Product> findAll(PageAble pageable, Sort sort);
}
