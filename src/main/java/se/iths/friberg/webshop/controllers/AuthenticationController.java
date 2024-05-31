package se.iths.friberg.webshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.friberg.webshop.models.MyUserDetails;
import se.iths.friberg.webshop.services.AuthenticationService;
import se.iths.friberg.webshop.services.ModelService;
import se.iths.friberg.webshop.session.SessionManager;

@Controller
public class AuthenticationController{

    private final AuthenticationService authService;
    private final SessionManager sessionManager;
    private final ModelService modelService;

    @Autowired
    public AuthenticationController(AuthenticationService authService, SessionManager sessionManager,
                                    ModelService modelService){
        this.authService = authService;
        this.sessionManager = sessionManager;
        this.modelService = modelService;
    }

    @PostMapping("/login-handler")
    public String loginHandler(Model model){
        modelService.addHeaderAttributes(model);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();

        sessionManager.setUser(userDetails.getUser());
        sessionManager.setLoggedIn();

        if(sessionManager.isLoggedIn()){
            return "redirect:/";
        }else{
            return "/login";
        }
    }
    
    @GetMapping("/register")
    public String register(Model model){
        modelService.addHeaderAttributes(model);

        return "register";
    }
    
    @PostMapping("/register")
    public String postRegister(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String passwordRepeat,
                               Model model){
        modelService.addHeaderAttributes(model);

        String errorMessage = authService.validateRegistration(username, password, passwordRepeat);
        model.addAttribute("regMessage", errorMessage);

        if(errorMessage.isBlank()){
            return "redirect:/";
        }else{
            return "/register";
        }
    }

    @GetMapping("/logout-handler")
    public String logoutHandler(Model model){
        modelService.addHeaderAttributes(model);

        sessionManager.userLogOut();
        return "redirect:/";
    }
    
}
