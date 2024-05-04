/*
 * 
 * package com.ramki.ecommdeliverytime.services;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ramki.ecommdeliverytime.exceptions.AddressNotFoundException;
import com.ramki.ecommdeliverytime.exceptions.ProductNotFoundException;
import com.ramki.ecommdeliverytime.libraries.GoogleMapsApi;
import com.ramki.ecommdeliverytime.libraries.models.GLocation;
import com.ramki.ecommdeliverytime.models.Address;
import com.ramki.ecommdeliverytime.models.DeliveryHub;
import com.ramki.ecommdeliverytime.models.Product;
import com.ramki.ecommdeliverytime.models.Seller;
import com.ramki.ecommdeliverytime.repositories.AddressRepository;
import com.ramki.ecommdeliverytime.repositories.DeliveryHubRepository;
import com.ramki.ecommdeliverytime.repositories.ProductRepository;
import com.ramki.ecommdeliverytime.repositories.SellerRepository;
import com.ramki.ecommdeliverytime.repositories.UserRepository;


@Service
public class ProductServiceImpl implements ProductService {
    
    private SellerRepository sellerRepository;
    private DeliveryHubRepository deliveryHubRepository;
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private ProductRepository productRepository;
    
    

    public ProductServiceImpl(
         SellerRepository sellerRepository
        ,DeliveryHubRepository deliveryHubRepository
        ,UserRepository userRepository
        ,AddressRepository addressRepository
        ,ProductRepository productRepository
     ) {
        super();
        this.sellerRepository = sellerRepository;
        this.deliveryHubRepository = deliveryHubRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
    }


    //addressId is end customer (user) address Id
    //from productId get seller; because assumption is one product is sold by one seller
        //with seller you have seller address
    //using seller address zipcode get DeliveryHub address because for every zip code there is one delivery hub
    //from delivery hub object, you have delivery hub address
    //seller address -----> delivery hub address  -------> user address 
    //get time for seller to delivery hub using longitude and latitude and google lib classes
    //get time for delivery hub to user 
    //add them up
    @Override
    public Date estimateDeliveryDate(int productId, int addressId) throws ProductNotFoundException, AddressNotFoundException {  
        
        //private double sellerLongitude;
        //private double sellerLatitude;
        //private double deliveryhubLongitude;
        //private doublt deliveryhubLatitude;
        
        GLocation sellerLocation = new GLocation();
        GLocation dhubLocation = new GLocation();
        GLocation userLocation = new GLocation();
        
        GoogleMapsApi gapi = new GoogleMapsApi();
        
        //Optional<User> userOptional = userRepository.findByAddressId(addressId);
        //User user = userOptional.get();
        
        Optional<Address> userAddressOptional = addressRepository.findById(addressId);
        
        if(userAddressOptional.isEmpty()) {
            throw new AddressNotFoundException("for addressId from request, no address found");
        }
        Address userAddress = userAddressOptional.get();
        
        Optional<Product> productOptional = productRepository.findById(productId);
        
        if(productOptional.isEmpty()) {
            throw new ProductNotFoundException("for given productId, no product found");
        }
        
        Product product = productOptional.get();
        
        Seller seller = product.getSeller();
        
        Optional<DeliveryHub> deliveryHubOptional = deliveryHubRepository.findByAddress_ZipCode(seller.getAddress().getZipCode());
        
        if(deliveryHubOptional.isEmpty()) {
            throw new AddressNotFoundException("No delivery hub in given product id - seller - seller address zip code");
        }
        
        
        DeliveryHub deliveryHub = deliveryHubOptional.get();
        
        sellerLocation.setLatitude(seller.getAddress().getLatitude());
        sellerLocation.setLongitude(seller.getAddress().getLongitude());
        
        dhubLocation.setLatitude(deliveryHub.getAddress().getLatitude());
        dhubLocation.setLongitude(deliveryHub.getAddress().getLatitude());
        
        userLocation.setLatitude(userAddress.getLatitude());
        userLocation.setLongitude(userAddress.getLongitude());

        int sellerToDhubSeconds = gapi.estimate(sellerLocation, dhubLocation);
        int dhubToUserSeconds = gapi.estimate(dhubLocation, userLocation);
        
        int totalSeconds = sellerToDhubSeconds + dhubToUserSeconds;
        
        // Get the current date and time
        Date currentDate = new Date();

        // Create a Calendar instance and set it to the current date and time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Add seconds to the current time
        //int secondsToAdd = 60; // Change this to the number of seconds you want to add
        calendar.add(Calendar.SECOND, totalSeconds);

        // Get the modified date and time
        Date newDate = calendar.getTime();
        
        
        return newDate;
    }

}

*/

/*

package com.ramki.ecommdeliverytime.services;


import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import com.ramki.ecommdeliverytime.exceptions.AddressNotFoundException;
import com.ramki.ecommdeliverytime.exceptions.ProductNotFoundException;
import com.ramki.ecommdeliverytime.libraries.models.GLocation;
import com.ramki.ecommdeliverytime.libraries.GoogleMapsApi;
import com.ramki.ecommdeliverytime.models.Address;
import com.ramki.ecommdeliverytime.models.DeliveryHub;
import com.ramki.ecommdeliverytime.models.Product;
import com.ramki.ecommdeliverytime.models.Seller;
import com.ramki.ecommdeliverytime.repositories.AddressRepository;
import com.ramki.ecommdeliverytime.repositories.DeliveryHubRepository;
import com.ramki.ecommdeliverytime.repositories.ProductRepository;
import com.ramki.ecommdeliverytime.repositories.SellerRepository;
import com.ramki.ecommdeliverytime.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    
    private SellerRepository sellerRepository;
    private DeliveryHubRepository deliveryHubRepository;
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private ProductRepository productRepository;
    
    

    public ProductServiceImpl(
         SellerRepository sellerRepository
        ,DeliveryHubRepository deliveryHubRepository
        ,UserRepository userRepository
        ,AddressRepository addressRepository
        ,ProductRepository productRepository
     ) {
        super();
        this.sellerRepository = sellerRepository;
        this.deliveryHubRepository = deliveryHubRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
    }


    //addressId is end customer (user) address Id
    //from productId get seller; because assumption is one product is sold by one seller
        //with seller you have seller address
    //using seller address zipcode get DeliveryHub address because for every zip code there is one delivery hub
    //from delivery hub object, you have delivery hub address
    //seller address -----> delivery hub address  -------> user address 
    //get time for seller to delivery hub using longitude and latitude and google lib classes
    //get time for delivery hub to user 
    //add them up
    @Override
    public Date estimateDeliveryDate(int productId, int addressId) throws ProductNotFoundException, AddressNotFoundException {  
        
        //private double sellerLongitude;
        //private double sellerLatitude;
        //private double deliveryhubLongitude;
        //private doublt deliveryhubLatitude;
        
        GLocation sellerLocation = new GLocation();
        GLocation dhubLocation = new GLocation();
        GLocation userLocation = new GLocation();
        
        GoogleMapsApi gapi = new GoogleMapsApi();
        
        //Optional<User> userOptional = userRepository.findByAddressId(addressId);
        //User user = userOptional.get();
        
        Optional<Address> userAddressOptional = addressRepository.findById(addressId);
        
        if(userAddressOptional.isEmpty()) {
            throw new AddressNotFoundException("for addressId from request, no address found");
        }
        Address userAddress = userAddressOptional.get();
        
        Optional<Product> productOptional = productRepository.findById(productId);
        
        if(productOptional.isEmpty()) {
            throw new ProductNotFoundException("for given productId, no product found");
        }
        
        Product product = productOptional.get();
        
        Seller seller = product.getSeller();
        
        Optional<DeliveryHub> deliveryHubOptional = deliveryHubRepository.findByAddress_ZipCode(seller.getAddress().getZipCode());
        
        if(deliveryHubOptional.isEmpty()) {
            throw new AddressNotFoundException("No delivery hub in given product id - seller - seller address zip code");
        }
        
        
        DeliveryHub deliveryHub = deliveryHubOptional.get();
        
        sellerLocation.setLatitude(seller.getAddress().getLatitude());
        sellerLocation.setLongitude(seller.getAddress().getLongitude());
        
        dhubLocation.setLatitude(deliveryHub.getAddress().getLatitude());
        dhubLocation.setLongitude(deliveryHub.getAddress().getLatitude());
        
        userLocation.setLatitude(userAddress.getLatitude());
        userLocation.setLongitude(userAddress.getLongitude());

        int sellerToDhubSeconds = gapi.estimate(sellerLocation, dhubLocation);
        int dhubToUserSeconds = gapi.estimate(dhubLocation, userLocation);
        
        int totalSeconds = sellerToDhubSeconds + dhubToUserSeconds;
        
        // Get the current date and time
        Date currentDate = new Date();

        // Create a Calendar instance and set it to the current date and time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Add seconds to the current time
        //int secondsToAdd = 60; // Change this to the number of seconds you want to add
        calendar.add(Calendar.SECOND, totalSeconds);

        // Get the modified date and time
        Date newDate = calendar.getTime();
        
        
        return newDate;
    }

}
*/

package com.ramki.ecommdeliverytime.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ramki.ecommdeliverytime.adapters.MapsAdapter;
import com.ramki.ecommdeliverytime.exceptions.AddressNotFoundException;
import com.ramki.ecommdeliverytime.exceptions.ProductNotFoundException;
import com.ramki.ecommdeliverytime.models.Address;
import com.ramki.ecommdeliverytime.models.DeliveryHub;
import com.ramki.ecommdeliverytime.models.Product;
import com.ramki.ecommdeliverytime.models.Seller;
import com.ramki.ecommdeliverytime.repositories.AddressRepository;
import com.ramki.ecommdeliverytime.repositories.DeliveryHubRepository;
import com.ramki.ecommdeliverytime.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;
    private AddressRepository addressRepository;
    private DeliveryHubRepository deliveryHubRepository;

    private MapsAdapter mapsAdapter;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, AddressRepository addressRepository, DeliveryHubRepository deliveryHubRepository, MapsAdapter mapsAdapter) {
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
        this.deliveryHubRepository = deliveryHubRepository;
        this.mapsAdapter = mapsAdapter;
    }

    @Override
    public Date estimateDeliveryDate(int productId, int addressId) throws ProductNotFoundException, AddressNotFoundException {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        Address userAddress = this.addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException("Address not found"));

        Seller seller = product.getSeller();
        Address sellerAddress = seller.getAddress();
        DeliveryHub deliveryHub = this.deliveryHubRepository.findByAddress_ZipCode(sellerAddress.getZipCode()).orElseThrow(() -> new AddressNotFoundException("Delivery hub not found"));

        //Calculate time estimate between seller and hub
        int estimate = mapsAdapter.getEstimatedTime(sellerAddress.getLatitude(), sellerAddress.getLongitude(), deliveryHub.getAddress().getLatitude(), deliveryHub.getAddress().getLongitude());
        //Calculate time estimate between hub and user
        estimate += mapsAdapter.getEstimatedTime(deliveryHub.getAddress().getLatitude(), deliveryHub.getAddress().getLongitude(), userAddress.getLatitude(), userAddress.getLongitude());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, estimate);
        return calendar.getTime();
    }
}

