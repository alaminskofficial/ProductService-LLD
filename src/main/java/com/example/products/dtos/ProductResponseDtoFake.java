package com.example.products.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDtoFake {
    private Long id;
    private String title;
    private float price;
    private String description;
    private String category;
    private String image;
}
