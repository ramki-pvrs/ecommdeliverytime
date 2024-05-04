package com.ramki.ecommdeliverytime.libraries;

import java.util.Random;

import com.ramki.ecommdeliverytime.libraries.models.GLocation;

import lombok.Data;

@Data
public class GoogleMapsApi {
    public int estimate(GLocation src, GLocation dest) {
        // Call google maps service to get the number of seconds required to travel from src to dest
        Random random = new Random();
        return random.nextInt(100000000) + 1;
    }
}
