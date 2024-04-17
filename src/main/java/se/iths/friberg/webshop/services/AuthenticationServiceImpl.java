package se.iths.friberg.webshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.friberg.webshop.db.entities.User;
import se.iths.friberg.webshop.db.repositories.UserRepository;
import se.iths.friberg.webshop.session.SessionDetails;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    @Autowired
    UserRepository userRepo;
    @Autowired
    SessionDetails sessionDetails;

    @Override
    public void validateLogin(String username, String password){
        User user = userRepo.findByUsername(username);

        boolean isValidLogin = (user != null && user.getPassword().equals(password));
        if(isValidLogin){
            sessionDetails.setUser(user);
        }
    }
    @Override
    public String validateRegistration(String username,
                                       String password,
                                       String passwordRepeat){
        boolean usernameIsTaken = userRepo.existsByUsername(username);
        if(usernameIsTaken){
            return "Username is already taken.";
        }

        if(!validateUsername(username)){
            return "Invalid username";
        }else if(!validatePassword(password)){
            return "Invalid password";
        }else if(!verifyPassword(password,passwordRepeat)){
            return "Passwords do not match";
        }

        User newUser = new User(username,password);
        userRepo.save(newUser);
        sessionDetails.setUser(newUser);
        return "";
    }
    private boolean validateUsername(String username){
        return username.matches("^[a-zA-Z0-9]{5,20}$");
    }
    private boolean validatePassword(String password){
        return password.matches("^[a-zA-Z0-9!?&%@]{5,20}$");
    }
    private boolean verifyPassword(String password, String passwordRepeat){
        return password.equals(passwordRepeat);
    }

}