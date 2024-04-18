package se.iths.friberg.webshop.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import se.iths.friberg.webshop.db.entities.User;
import se.iths.friberg.webshop.dto.ShoppingCart;

@Component
@SessionScope
public class SessionDetails{

    private User user;
    private ShoppingCart shoppingCart;
    private boolean isLoggedIn;

    public SessionDetails(){
        this.shoppingCart = new ShoppingCart();
    }

    public void userLogOut(){
        this.user = null;
        this.shoppingCart = new ShoppingCart();
        setLoggedIn();
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
        setLoggedIn();
    }

    public ShoppingCart getShoppingCart(){
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart){
        this.shoppingCart = shoppingCart;
    }

    public boolean isLoggedIn(){
        return isLoggedIn;
    }

    public void setLoggedIn(){
        isLoggedIn = (user != null);
    }
}
