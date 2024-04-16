package se.iths.friberg.webshop.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import se.iths.friberg.webshop.db.entities.User;

@Component
@SessionScope
public class SessionDetails{

    private User user;
//    private ShoppingCart shoppingCart;
    private boolean isLoggedIn;

    public void end(){
        this.user = null;
        this.isLoggedIn = false;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
        setLoggedIn();
    }

    public boolean isLoggedIn(){
        return isLoggedIn;
    }

    public void setLoggedIn(){
        isLoggedIn = (user != null);
    }
}
