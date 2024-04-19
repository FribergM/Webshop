package se.iths.friberg.webshop.models;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart{
    private List<CartItem> cartItems;

    public ShoppingCart(){
        this.cartItems = new ArrayList<>();
    }

    public void addToCart(CartItem cartItem){
        cartItems.add(cartItem);
    }
    public void removeFromCart(int index){
        cartItems.remove(index);
    }
    public int getCartSize(){
        return getCartItems().size();
    }

    public int getItemQuantity(int index){
        return getCartItems().get(index).getQuantity();
    }

    public void changeItemQuantity(int index, int newQuantity){
        getCartItems().get(index).setQuantity(newQuantity);
    }

    public List<CartItem> getCartItems(){
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems){
        this.cartItems = cartItems;
    }
}
