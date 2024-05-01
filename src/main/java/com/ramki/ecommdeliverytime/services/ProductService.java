package com.ramki.ecommdeliverytime.services;

import java.util.Date;

import com.ramki.ecommdeliverytime.exceptions.AddressNotFoundException;
import com.ramki.ecommdeliverytime.exceptions.ProductNotFoundException;

public interface ProductService {
    public Date estimateDeliveryDate(int productId, int addressId) throws ProductNotFoundException, AddressNotFoundException;
}