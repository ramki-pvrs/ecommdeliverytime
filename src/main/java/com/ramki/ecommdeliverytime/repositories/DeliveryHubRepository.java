package com.ramki.ecommdeliverytime.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramki.ecommdeliverytime.models.DeliveryHub;



public interface DeliveryHubRepository extends JpaRepository<DeliveryHub, Integer> {
    Optional<DeliveryHub> findById(int deliveryHubId);
    
    Optional<DeliveryHub> findByAddressId(int addressId);
    
    Optional<DeliveryHub> findByZipCode(String zipCode);
}
