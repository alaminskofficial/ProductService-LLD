package com.example.products.inheritance.singleTable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
@DiscriminatorValue("1")
@Entity
public class Mentor extends User{
    private String noOfMentorship;
    private String avgRating;
    private String company;
}
