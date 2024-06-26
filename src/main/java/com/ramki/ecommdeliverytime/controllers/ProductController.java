package com.ramki.ecommdeliverytime.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ramki.ecommdeliverytime.dtos.DeliveryEstimateRequestDto;
import com.ramki.ecommdeliverytime.dtos.DeliveryEstimateResponseDto;
import com.ramki.ecommdeliverytime.dtos.ResponseStatus;
import com.ramki.ecommdeliverytime.services.ProductService;

@Controller
public class ProductController {
    
    /*
     1. Instantiate the required service; our service is ProductService which has one method to cals delivery time
     2. Autowire that service object
     3. 
     */
    private ProductService productService;
    
    //Unnecessary `@Autowired` because there is only one constructor
    @Autowired 
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    
    //return the response DTO
    public DeliveryEstimateResponseDto estimateDeliveryTime(DeliveryEstimateRequestDto requestDto) {
        DeliveryEstimateResponseDto responseDto = new DeliveryEstimateResponseDto();
        
        try {
            Date estimateDeliveryDate  = (Date)productService.estimateDeliveryDate(requestDto.getProductId(), requestDto.getAddressId());
            responseDto.setExpectedDeliveryDate(estimateDeliveryDate );
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            return responseDto;
        } catch(Exception ex) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            return responseDto;
        }
    }
    
}
