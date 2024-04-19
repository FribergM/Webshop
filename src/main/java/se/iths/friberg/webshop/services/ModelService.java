package se.iths.friberg.webshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import se.iths.friberg.webshop.session.SessionManager;

@Service
public class ModelService{

    private final SessionManager sessionManager;

    @Autowired
    public ModelService(SessionManager sessionManager){
        this.sessionManager = sessionManager;
    }

    public void addHeaderAttributes(Model model){
        model.addAttribute("loggedIn", sessionManager.isLoggedIn());
        model.addAttribute("cartQuant", sessionManager.getShoppingCart().getCartSize());
        model.addAttribute("isAdmin", sessionManager.userAdminStatus());
    }
}
