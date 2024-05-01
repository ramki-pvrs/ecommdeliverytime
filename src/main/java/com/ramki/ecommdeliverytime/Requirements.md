Problem Statement
Whenever we browse a product on Amazon/Flipkart, we see an estimated delivery date and time on the product page. This estimate tells us that if we buy the product now, then by what time we will receive the product. We want to build this functionality for our e-commerce platform.

Requirements
Products are stored at seller's warehouses. To ensure seamless and quick delivery experience, ecommerce companies setup delivery hubs at various locations. We can assume that each zipcode (pincode) has 1 delivery hub. Since each zipcode has 1 delivery hub, this is ideal spot from where the product can be delivered to the user.

Total time required to deliver a product to a user is made up of 2 parts:

Ship the product from seller warehouse to the delivery hub.
Ship the product from the delivery hub to the user's delivery location. Total time required = Time required to ship from seller warehouse to delivery hub + Time required to ship from delivery hub to user's delivery location. Add this total time required to the current time to get the estimated time.
We will use Google Maps API to calculate the time required to move from a src location to a destination location. We need to make sure that this integration is as loosely coupled as possible to ensure that we can easily switch to a different API in the future.

We have a address table which will be storing addresses of users, seller warehouses and delivery hubs. We can use this table to calculate the estimated delivery time.

Assumptions:

Each zip code has 1 delivery hub.
Each customer has 1 stored address.
Each seller has 1 warehouse.
Each product will be sold by 1 seller.
Each seller can sell multiple products.
Request: The request will contain the following information:

Product ID - The product for which we want to calculate the estimated delivery time.
Address ID - The delivery address of the user.
Response: The response will contain the following information:

Estimated date - The estimated date and time of delivery.
Response status - This will be either success or failure.