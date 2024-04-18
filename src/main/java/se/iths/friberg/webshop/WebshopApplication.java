package se.iths.friberg.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebshopApplication{

    public static void main(String[] args){
        SpringApplication.run(WebshopApplication.class, args);

        //TODO Checklist:
        // - Checkout functionality with confirmation page
        // - Store Order in database
        // - Implement User Roles
        // - Admin login with expediting orders
        // - Admin adding new products
        // - Tests for key components E.g. Shopping cart.
        // - VG Spring Security authentication with database (JPA)

        //TODO Fluff:
        // - If product is already in cart, make it just increase quantity
        // - Add safeguard to not add multiple of same product (Beyond max order)
        // - Replace ProductService with ModelService(?)
        // - Check to make sure products are in stock at exact moment of placing order.
    }
}
