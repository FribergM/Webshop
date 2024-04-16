package se.iths.friberg.webshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.friberg.webshop.services.AuthenticationService;
import se.iths.friberg.webshop.session.SessionDetails;

@Controller
public class AuthenticationController{

    @Autowired
    AuthenticationService authService;
    @Autowired
    SessionDetails sessionDetails;

    @GetMapping("/login")
    public String baseLoginPage(Model model){

        model.addAttribute("loggedIn", sessionDetails.isLoggedIn());

        if(sessionDetails.isLoggedIn()){
            return "redirect:/";
        }else{
            return "/login";
        }

    }
    @PostMapping("/login")
    public String postLogin(@RequestParam String username,
                            @RequestParam String password,
                            Model model){

        authService.validateLogin(username,password);
        sessionDetails.setLoggedIn();


        model.addAttribute("loggedIn", sessionDetails.isLoggedIn());
        model.addAttribute("loginMessage", "Incorred username/password.");

        if(sessionDetails.isLoggedIn()){
            return "redirect:/";
        }else{
            return "/login";
        }

    }
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("loggedIn", sessionDetails.isLoggedIn());
        return "register";
    }
    @PostMapping("/register")
    public String postRegister(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String passwordRepeat,
                               Model model){

        String errorMessage = authService.validateRegistration(username, password, passwordRepeat);
        model.addAttribute("loggedIn", sessionDetails.isLoggedIn());
        model.addAttribute("regMessage", errorMessage);

        if(errorMessage.isBlank()){
            return "redirect:/";
        }else{
            return "/register";
        }
    }
    @GetMapping("/logout")
    public String logout(){

        sessionDetails.end();
        return "redirect:/";
    }
}
