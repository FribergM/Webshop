package se.iths.friberg.webshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.friberg.webshop.dto.CartItem;
import se.iths.friberg.webshop.services.ModelService;
import se.iths.friberg.webshop.services.ShoppingCartService;
import se.iths.friberg.webshop.session.SessionDetails;

@Controller
public class ShoppingCartController{

    @Autowired
    SessionDetails sessionDetails;
    @Autowired
    ModelService modelService;
    @Autowired
    ShoppingCartService cartService;

    @GetMapping("/cart")
    public String shoppingCart(Model model){
        modelService.addHeaderAttributes(model);

        model.addAttribute("cartIsEmpty", sessionDetails.getShoppingCart().getCartItems().isEmpty());
        model.addAttribute("cartItems",sessionDetails.getShoppingCart().getCartItems());
        model.addAttribute("totalPrice",cartService.calculateTotalPrice());
        return "shoppingCart";
    }
    @PostMapping("changeQuantity")
    public String changeQuantity(@RequestParam String action,
                                 @RequestParam(value = "itemId") int itemIndex,
                                 @RequestParam Boolean placeOrder,
                                 Model model){
        modelService.addHeaderAttributes(model);

        cartService.changeItemQuantity(action,itemIndex);
        model.addAttribute("cartIsEmpty", sessionDetails.getShoppingCart().getCartItems().isEmpty());
        model.addAttribute("cartItems",sessionDetails.getShoppingCart().getCartItems());
        model.addAttribute("totalPrice",cartService.calculateTotalPrice());

        if(placeOrder){
            return "order-confirmation";
        }

        return "shoppingCart";
    }
}
