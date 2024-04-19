package se.iths.friberg.webshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.friberg.webshop.db.entities.Product;
import se.iths.friberg.webshop.db.repositories.ProductRepository;
import se.iths.friberg.webshop.models.CartItem;
import se.iths.friberg.webshop.session.SessionManager;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService{

    private final ProductRepository productRepo;
    private final SessionManager sessionManager;

    @Autowired
    public ShoppingCartService(ProductRepository productRepo, SessionManager sessionManager){
        this.productRepo = productRepo;
        this.sessionManager = sessionManager;
    }

    public String findAndAddToCart(Long productId, int quantity){
        Optional<Product> product = productRepo.findById(productId);

        if(product.isPresent()){
            CartItem cartItem = new CartItem(product.get(),quantity);
            sessionManager.getShoppingCart().addToCart(cartItem);

            return quantity + "x " + product.get().getProductName() + " added to cart.";
        }else{
            //Should never be reached
            return "Product does not exist!";
        }
    }

    public void changeItemQuantity(String action, int itemIndex){
        int quantity = sessionManager.getShoppingCart().getItemQuantity(itemIndex);

        if(action.equals("increase")){
            quantity++;
        }else if(action.equals("decrease")){
            quantity--;
        }

        if(quantity<1){
            sessionManager.getShoppingCart().removeFromCart(itemIndex);
        }else{
            sessionManager.getShoppingCart().changeItemQuantity(itemIndex,quantity);
        }

    }

    public double calculateTotalPrice(){
        double totalPrice = 0;
        List<CartItem> cart = sessionManager.getShoppingCart().getCartItems();
        for(CartItem item : cart){
            totalPrice+= (item.getProductPrice()*item.getQuantity());

        }
        return (double) Math.round(totalPrice * 100) / 100;
    }

}
