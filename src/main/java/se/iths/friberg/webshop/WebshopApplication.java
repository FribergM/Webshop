package se.iths.friberg.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebshopApplication{

    public static void main(String[] args){
        SpringApplication.run(WebshopApplication.class, args);

        //TODO Checklist:
        // - Show actual order details in admin order pages.
        // - VG Spring Security authentication with database (JPA) or sending Order confirmation email

        //TODO Fluff:
        // - If product is already in cart, make it just increase quantity
        // - Add safeguard to not add multiple of same product (Beyond max order)
        // - Check to make sure products are in stock at exact moment of placing order.
    }
}
