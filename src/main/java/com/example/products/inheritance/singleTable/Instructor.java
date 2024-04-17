package com.example.products.inheritance.singleTable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
@DiscriminatorValue("3")
@Entity
public class Instructor extends User{
    private String specilization;
    private String experience;
}
