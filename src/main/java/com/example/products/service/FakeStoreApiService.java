package com.example.products.service;

import com.example.products.dtos.ProductRequestDtoFake;
import com.example.products.dtos.ProductResponseDtoFake;
import com.example.products.exceptions.ProductNotPresentException;
import com.example.products.models.Category;
import com.example.products.models.Product;
import com.example.products.repositories.CategoryRepository;
import com.example.products.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class FakeStoreApiService implements IProductService{
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    RedisTemplate redisTemplate;
    private static final long GLOBAL_EXPIRATION_TIME = 60; // in minutes

    private void cacheValueWithGlobalExpiry(String key, Object value) {
        redisTemplate.opsForHash().put(key, "hash_key" ,value);
        redisTemplate.expire(key, GLOBAL_EXPIRATION_TIME, TimeUnit.MINUTES);
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotPresentException {
        String key = "product_" + id;
        if(!redisTemplate.opsForHash().hasKey(key , key)){
            if(id>20 && id<=40){
                throw new ProductNotPresentException();
            }
            if(id>40){
                throw new ArithmeticException();
            }

            ProductResponseDtoFake response = restTemplate.getForObject(
                    "https://fakestoreapi.com/products/" + id,
                    ProductResponseDtoFake.class);

            Product product = getProductFromResponseDTO(response);
            cacheValueWithGlobalExpiry(key, product);
        }
        return (Product) redisTemplate.opsForHash().get(key,key);
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
