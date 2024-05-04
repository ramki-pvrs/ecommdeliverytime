package com.ramki.ecommdeliverytime.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ramki.ecommdeliverytime.models.DeliveryHub;


@Repository
public interface DeliveryHubRepository extends JpaRepository<DeliveryHub, Integer> {
    Optional<DeliveryHub> findById(int deliveryHubId);
    
    Optional<DeliveryHub> findByAddressId(int addressId);
    
    @EntityGraph(attributePaths = "address")
    Optional<DeliveryHub> findByAddress_ZipCode(String zipCode);
}
