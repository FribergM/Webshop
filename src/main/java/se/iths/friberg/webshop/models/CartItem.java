package se.iths.friberg.webshop.models;

import se.iths.friberg.webshop.db.entities.Product;

public class CartItem{
    private Product product;
    private int quantity;
    private double combinedPrice;

    public CartItem(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
        setCombinedPrice();
    }

    public double getCombinedPrice(){
        return combinedPrice;
    }

    public void setCombinedPrice(){
        combinedPrice = (double) Math.round((getProductPrice() * quantity) * 100) /100;
    }

    public String getProductName(){
        return product.getProductName();
    }
    public double getProductPrice(){
        return product.getPrice();
    }
    public int getProductStock(){
        return product.getStock();
    }

    public Product getProduct(){
        return product;
    }

    public void setProduct(Product product){
        this.product = product;
        setCombinedPrice();
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
        setCombinedPrice();
    }
}
