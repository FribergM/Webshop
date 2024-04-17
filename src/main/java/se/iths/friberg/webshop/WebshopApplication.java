package se.iths.friberg.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebshopApplication{

    public static void main(String[] args){
        SpringApplication.run(WebshopApplication.class, args);
        //TODO
        // - Stopped at productPage

        //TODO Checklist:
        // - Categories of products
        // - Search function by name
        // - Shopping Cart that updates when products are added/removed
        // - Checkout functionality with confirmation page
        // - Store Order in database
        // - Implement User Roles
        // - Admin login with expediting orders
        // - Admin adding new products
        // - Tests for key components E.g. Shopping cart.
        // - Spring Security authentication with database (JPA)
    }
}
