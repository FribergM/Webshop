package se.iths.friberg.webshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import se.iths.friberg.webshop.session.SessionDetails;

@Service
public class ModelService{

    @Autowired
    SessionDetails sessionDetails;

    public void addHeaderAttributes(Model model){
        model.addAttribute("loggedIn", sessionDetails.isLoggedIn());
        model.addAttribute("cartQuant", sessionDetails.getShoppingCart().getCartSize());
    }
}
