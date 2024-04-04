package com.example.products.service;

import com.example.products.dtos.ProductRequestDtoFake;
import com.example.products.dtos.ProductResponseDtoFake;
import com.example.products.exceptions.ProductNotPresentException;
import com.example.products.models.Category;
import com.example.products.models.Product;
import com.example.products.repositories.CategoryRepository;
import com.example.products.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreApiService implements IProductService{
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public Product getSingleProduct(Long id) throws ProductNotPresentException {
        if(id>20 && id<=40){
            throw new ProductNotPresentException();
        }
        if(id>40){
            throw new ArithmeticException();
        }

        ProductResponseDtoFake response = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                ProductResponseDtoFake.class);

        return getProductFromResponseDTO(response);
    }

    @Override
    public List<Category> getAllPCategories() {
        String [] categories = restTemplate.getForObject(
                "https://fakestoreapi.com/products/categories",
                String[].class);
        List<Category> output = new ArrayList<>();
        if (categories != null) {
            for (String category : categories) {
                Category category1 = new Category();
                category1.setId((long) category.hashCode());
                category1.setName(category);
                output.add(category1);
            }
        }
        return output;
    }

    @Override
    public List<Product> getProductsByCategory(String id) {
        ProductResponseDtoFake[] categories = restTemplate.getForObject(
                "https://fakestoreapi.com/products/category/" + id,
                ProductResponseDtoFake[].class);
        List<Product> output = new ArrayList<>();
        if (categories != null) {
            for (ProductResponseDtoFake category : categories) {
                output.add(getProductFromResponseDTO(category));
            }
        }
        return output;
    }

    @Override
    public Product addProduct(ProductRequestDtoFake reqProduct) {
        Product product = new Product();
        Category category = new Category();
        category.setName(reqProduct.getCategory());
        categoryRepository.save(category);
        product.setName(reqProduct.getTitle());
        product.setCategory(category);
        product.setDescription(reqProduct.getDescription());
        product.setPrice(reqProduct.getPrice());
        product.setImage(reqProduct.getImage());
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        ProductResponseDtoFake[] products = restTemplate.getForObject(
                "https://fakestoreapi.com/products/",
                ProductResponseDtoFake[].class);

        List<Product> output = new ArrayList<>();
        if (products != null) {
            for (ProductResponseDtoFake productResponseDto : products) {
                output.add(getProductFromResponseDTO(productResponseDto));
            }
        }
        return  productRepository.saveAll(output);
    }

    private Product getProductFromResponseDTO(ProductResponseDtoFake response) {

        Product product = new Product();
        product.setId(response.getId());
        product.setName(response.getTitle());
        product.setDescription(response.getDescription());
        product.setPrice(response.getPrice());
        if(categoryRepository.findByName(response.getCategory()) != null){
            product.setCategory(categoryRepository.findByName(response.getCategory()));
        }else{
            Category category = new Category();
            category.setName(response.getCategory());
            product.setCategory(categoryRepository.save(category));

        }
        product.setImage(response.getImage());
        return product;
    }
}
