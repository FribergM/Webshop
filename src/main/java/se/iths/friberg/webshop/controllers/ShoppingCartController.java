package se.iths.friberg.webshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.iths.friberg.webshop.session.SessionDetails;

@Controller
public class ShoppingCartController{

    @Autowired
    SessionDetails sessionDetails;

    @GetMapping("/cart")
    public String shoppingCart(Model model){
        model.addAttribute("loggedIn", sessionDetails.isLoggedIn());
        return "/shoppingCart";
    }
}
