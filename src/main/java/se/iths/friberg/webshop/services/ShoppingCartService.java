package se.iths.friberg.webshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.friberg.webshop.db.entities.Product;
import se.iths.friberg.webshop.db.repositories.ProductRepository;
import se.iths.friberg.webshop.dto.CartItem;
import se.iths.friberg.webshop.dto.ShoppingCart;
import se.iths.friberg.webshop.session.SessionDetails;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService{

    @Autowired
    ProductRepository productRepo;
    @Autowired
    SessionDetails sessionDetails;

    public String addProductToCart(Long productId, int quantity){
        Optional<Product> product = productRepo.findById(productId);

        if(product.isPresent()){
            CartItem cartItem = new CartItem(product.get(),quantity);
            sessionDetails.getShoppingCart().addToCart(cartItem);

            return quantity + "x " + product.get().getProductName() + " added to cart.";
        }else{
            //Should never be reached
            return "Product does not exist!";
        }
    }

    public void changeItemQuantity(String action, int itemIndex){
        int quantity = sessionDetails.getShoppingCart().getItemQuantity(itemIndex);

        if(action.equals("increase")){
            quantity++;
        }else if(action.equals("decrease")){
            quantity--;
        }

        if(quantity<1){
            sessionDetails.getShoppingCart().removeFromCart(itemIndex);
        }else{
            sessionDetails.getShoppingCart().changeItemQuantity(itemIndex,quantity);
        }

    }

    public double calculatePrice(Product product, int quantity){
        double totalPrice = product.getPrice() * quantity;
        return (double) Math.round(totalPrice * 100) / 100;
    }
    public double calculateTotalPrice(){
        double totalPrice = 0;
        List<CartItem> cart = sessionDetails.getShoppingCart().getCartItems();
        for(CartItem item : cart){
            totalPrice+= (item.getProductPrice()*item.getQuantity());

        }
        return (double) Math.round(totalPrice * 100) / 100;
    }

}
