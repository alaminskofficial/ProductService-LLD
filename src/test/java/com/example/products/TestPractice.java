package com.example.products;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPractice {
    @Test
    public void test() {
        // Arrange
        int a = 1 , b = 1;
        // Act
        int result = a + b;
        // Assert
        Assertions.assertEquals(2, result);
    }
}
