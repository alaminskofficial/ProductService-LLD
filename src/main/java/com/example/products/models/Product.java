package com.example.products.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@JsonSerialize
public class Product extends BaseModel implements Serializable {

    private String description;
    private String image;
    private float price;

    @ManyToOne
    private Category category;
}
