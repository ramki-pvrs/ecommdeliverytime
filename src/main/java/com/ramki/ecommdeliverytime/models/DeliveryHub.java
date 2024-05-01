package com.ramki.ecommdeliverytime.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity(name="deliveryhubs")
public class DeliveryHub extends BaseModel {
    private String name;
    
    @OneToOne
    private Address address;
}
