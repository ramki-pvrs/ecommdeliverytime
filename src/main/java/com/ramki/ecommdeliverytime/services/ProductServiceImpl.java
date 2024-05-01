package com.ramki.ecommdeliverytime.services;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import com.ramki.ecommdeliverytime.exceptions.AddressNotFoundException;
import com.ramki.ecommdeliverytime.exceptions.ProductNotFoundException;
import com.ramki.ecommdeliverytime.libraries.models.GLocation;
import com.ramki.ecommdeliverytime.libraries.models.GoogleMapsApi;
import com.ramki.ecommdeliverytime.models.Address;
import com.ramki.ecommdeliverytime.models.DeliveryHub;
import com.ramki.ecommdeliverytime.models.Seller;
import com.ramki.ecommdeliverytime.repositories.AddressRepository;
import com.ramki.ecommdeliverytime.repositories.DeliveryHubRepository;
import com.ramki.ecommdeliverytime.repositories.ProductRepository;
import com.ramki.ecommdeliverytime.repositories.SellerRepository;
import com.ramki.ecommdeliverytime.repositories.UserRepository;

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
        
        Optional<Seller> sellerOptional = sellerRepository.findByProductId(productId);
        if(sellerOptional.isEmpty()) {
            throw new ProductNotFoundException("No seller for given product id");
        }
        Seller seller = sellerOptional.get();
        
        Optional<DeliveryHub> deliveryHubOptional = deliveryHubRepository.findByZipCode(seller.getAddress().getZipCode());
        
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
