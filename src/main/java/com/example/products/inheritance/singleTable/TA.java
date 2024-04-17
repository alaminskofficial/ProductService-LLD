package com.example.products.inheritance.singleTable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
@DiscriminatorValue("2")
@Entity
public class TA extends User{
    private String noOfSessions;
    private String avgRating;
}
