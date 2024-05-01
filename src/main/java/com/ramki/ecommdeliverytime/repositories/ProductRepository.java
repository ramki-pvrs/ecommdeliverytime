package com.ramki.ecommdeliverytime.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramki.ecommdeliverytime.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findById(int productId);
}
