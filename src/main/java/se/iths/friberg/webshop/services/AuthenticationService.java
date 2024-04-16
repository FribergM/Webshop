package se.iths.friberg.webshop.services;

public interface AuthenticationService{
    void validateLogin(String username, String password);
    String validateRegistration(String username, String password, String passwordRepeat);
}
