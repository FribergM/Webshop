package se.iths.friberg.webshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.iths.friberg.webshop.session.SessionDetails;

@Controller
public class ProductController{

    @Autowired
    SessionDetails sessionDetails;

    @GetMapping(value = {"/","/products"})
    public String homePage(Model model){
        model.addAttribute("loggedIn", sessionDetails.isLoggedIn());
        return "/products";
    }
}
