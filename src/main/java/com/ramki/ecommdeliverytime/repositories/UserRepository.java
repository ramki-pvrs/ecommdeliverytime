package com.ramki.ecommdeliverytime.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ramki.ecommdeliverytime.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(int userId);
    
    //The @EntityGraph annotation helps to optimize the fetching strategy when retrieving related entities. 
    @EntityGraph(attributePaths = "addresses")
    Optional<User> findByAddresses_Id(int addressId);
    
    //List<EntityA> findByListOfItemsContaining(String itemValue);
}
