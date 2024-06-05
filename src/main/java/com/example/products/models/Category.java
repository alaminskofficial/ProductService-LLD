package com.example.products.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@JsonSerialize
public class Category extends BaseModel implements Serializable {
    @OneToMany(mappedBy = "category" ,fetch = FetchType.EAGER)
    List<Product> products;
}
