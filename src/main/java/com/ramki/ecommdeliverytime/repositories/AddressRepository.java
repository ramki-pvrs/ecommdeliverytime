package com.ramki.ecommdeliverytime.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramki.ecommdeliverytime.models.Address;


//@Repository Unnecessary `@Repository`
public interface AddressRepository extends JpaRepository<Address, Integer> {
    
    Optional<Address> findById(int id);
    
    
}
