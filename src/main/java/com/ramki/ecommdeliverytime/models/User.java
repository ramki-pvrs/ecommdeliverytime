package com.ramki.ecommdeliverytime.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity(name="users")
public class User extends BaseModel {
    private String name;
    private String email;
    
    @OneToMany
    private List<Address> addresses;
}
