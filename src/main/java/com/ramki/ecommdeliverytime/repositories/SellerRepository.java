package com.ramki.ecommdeliverytime.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramki.ecommdeliverytime.models.Seller;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
    Optional<Seller> findById(int sellerId);
    
    //Optional<Seller> findByProductId(int productId);
}
