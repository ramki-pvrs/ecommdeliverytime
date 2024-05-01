package com.ramki.ecommdeliverytime.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity(name="sellers")
public class Seller extends BaseModel {
    private String name;
    private String email;
    
    @OneToOne
    private Address address;
}
