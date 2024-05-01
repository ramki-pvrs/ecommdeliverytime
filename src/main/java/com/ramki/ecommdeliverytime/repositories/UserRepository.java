package com.ramki.ecommdeliverytime.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramki.ecommdeliverytime.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(int userId);
    
    Optional<User> findByAddressId(int addressId);
}
