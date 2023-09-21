package com.example;

import com.example.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Products, Integer> {
    public Products findByName (String name);
}
