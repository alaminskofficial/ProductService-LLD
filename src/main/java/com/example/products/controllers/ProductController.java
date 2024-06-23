package com.example.products.controllers;

import com.example.products.dtos.ProductRequestDtoFake;
import com.example.products.dtos.ProductResponseSelf;
import com.example.products.exceptions.ProductNotPresentException;
import com.example.products.models.Category;
import com.example.products.models.Product;
import com.example.products.repositories.ProductRepository;
import com.example.products.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @Autowired
    IProductService productService;
    @Autowired
    ProductRepository productRepository;
    @GetMapping("/product")
    public String doNothing(){
        System.out.println("request received at product service");
        return "Hello, From Product service";
    }
    @GetMapping("/getproducts")
    public ResponseEntity<?> getProducts(@RequestParam("pageNo") String pageNo ,@RequestParam("pageSize") String pageSize){
        Page<Product> products = productRepository.findAll(PageRequest.of(Integer.parseInt(pageNo), Integer.parseInt(pageSize) ,Sort.by("price")));
        if (products.isEmpty()) {
            return new ResponseEntity<>("No products found for the given page.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        // getting from 3rd party api's And add the data to the database
        return productService.getAllProducts()
                .stream()
                .filter(product -> product.getName().startsWith("S"))
                .collect(Collectors.toList());
    }
    @GetMapping("/products/search")
    public Product getProductsByName(@RequestParam("name") String name){
        return productRepository.findByName(name);

    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponseSelf> getSingleProduct(@PathVariable("id") Long id){

        Product product;
        try{
            product = productService.getSingleProduct(id);
        }catch (ProductNotPresentException e){
            ProductResponseSelf productResponseSelf = new ProductResponseSelf(null, "Product Doesn't exist");
            return new ResponseEntity<>(
                    productResponseSelf, HttpStatus.NOT_FOUND);
        }catch (ArithmeticException e){
            ProductResponseSelf productResponseSelf = new ProductResponseSelf(null, "Something went wrong");
            return new ResponseEntity<>(
                    productResponseSelf, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new ProductResponseSelf(product, "Success"), HttpStatus.OK);
    }
    // Exception handling using @ControllerAdvice in ProductControllerAdvice
    @GetMapping("/product/exception/{id}")
    public ResponseEntity<ProductResponseSelf> getSingleProductException(@PathVariable("id") Long id)
            throws ProductNotPresentException {

        Product product = productService.getSingleProduct(id);
        return new ResponseEntity<>(new ProductResponseSelf(product, "Success"), HttpStatus.OK);
    }

    @GetMapping("/products/categories")
    public List<Category> getAllCategories(){

        return productService.getAllPCategories();
    }

    @GetMapping("/products/category/{id}")
    public List<Product> getAllProductsInCategory(@PathVariable("id") String id){

        return productService.getProductsByCategory(id);
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody ProductRequestDtoFake requestDto){

        return productService.addProduct(requestDto);
    }

}
