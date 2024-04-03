package com.example.products.service;

import com.example.products.dtos.ProductResponseDto;
import com.example.products.models.Category;
import com.example.products.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreApiService implements IProductService{
    @Autowired
    RestTemplate restTemplate;

    @Override
    public Product getSingleProduct(Long id) {

        ProductResponseDto response = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                ProductResponseDto.class);

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
        ProductResponseDto[] categories = restTemplate.getForObject(
                "https://fakestoreapi.com/products/category/" + id,
                ProductResponseDto[].class);
        List<Product> output = new ArrayList<>();
        if (categories != null) {
            for (ProductResponseDto category : categories) {
                output.add(getProductFromResponseDTO(category));
            }
        }
        return output;
    }

    @Override
    public List<Product> getAllProducts() {
        ProductResponseDto[] products = restTemplate.getForObject(
                "https://fakestoreapi.com/products/",
                ProductResponseDto[].class);


        List<Product> output = new ArrayList<>();
        if (products != null) {
            for (ProductResponseDto productResponseDto : products) {
                output.add(getProductFromResponseDTO(productResponseDto));
            }
        }
        return output;
    }

    private Product getProductFromResponseDTO(ProductResponseDto response) {

        Product product = new Product();
        product.setId(response.getId());
        product.setName(response.getTitle());
        product.setDescription(response.getDescription());
        product.setPrice(response.getPrice());
        product.setCategory(new Category());
        product.getCategory().setName(response.getCategory());
        product.setImage(response.getImage());

        return product;
    }
}
