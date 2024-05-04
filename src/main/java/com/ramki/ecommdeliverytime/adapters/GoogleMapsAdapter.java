package com.ramki.ecommdeliverytime.adapters;

import org.springframework.stereotype.Component;

import com.ramki.ecommdeliverytime.libraries.GoogleMapsApi;
import com.ramki.ecommdeliverytime.libraries.models.GLocation;

@Component
public class GoogleMapsAdapter implements MapsAdapter {

    private final GoogleMapsApi googleMapsApi = new GoogleMapsApi();

    @Override
    public int getEstimatedTime(double srcLat, double srcLong, double destLat, double destLong) {
        GLocation src = new GLocation();
        src.setLatitude(srcLat);
        src.setLongitude(srcLong);
        GLocation dest = new GLocation();
        dest.setLatitude(destLat);
        dest.setLongitude(destLong);
        return googleMapsApi.estimate(src, dest);
    }

}
