package se.iths.friberg.webshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.friberg.webshop.services.ModelService;
import se.iths.friberg.webshop.services.OrderService;
import se.iths.friberg.webshop.services.ShoppingCartService;
import se.iths.friberg.webshop.session.SessionManager;

@Controller
public class ShoppingCartController{

    private final SessionManager sessionManager;
    private final ModelService modelService;
    private final ShoppingCartService cartService;
    private final OrderService orderService;

    @Autowired
    public ShoppingCartController(SessionManager sessionManager, ModelService modelService,
                                  ShoppingCartService cartService, OrderService orderService){
        this.sessionManager = sessionManager;
        this.modelService = modelService;
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @GetMapping("/cart")
    public String shoppingCart(Model model){
        modelService.addHeaderAttributes(model);

        model.addAttribute("cartIsEmpty", sessionManager.getShoppingCart().getCartItems().isEmpty());
        model.addAttribute("cartItems", sessionManager.getShoppingCart().getCartItems());
        model.addAttribute("totalPrice",cartService.calculateTotalPrice());
        return "shoppingcart";
    }
    @PostMapping("changeQuantity")
    public String changeQuantity(@RequestParam String action,
                                 @RequestParam(value = "itemId") int itemIndex,
                                 Model model){
        modelService.addHeaderAttributes(model);

        cartService.changeItemQuantity(action,itemIndex);
        model.addAttribute("cartIsEmpty", sessionManager.getShoppingCart().getCartItems().isEmpty());
        model.addAttribute("cartItems", sessionManager.getShoppingCart().getCartItems());
        model.addAttribute("totalPrice",cartService.calculateTotalPrice());

        return "shoppingcart";
    }
    @GetMapping("/order")
    public String orderConfirmation(Model model){
        modelService.addHeaderAttributes(model);

        if(!sessionManager.isLoggedIn()){
            return "login";
        }
        model.addAttribute("cartItems", sessionManager.getShoppingCart().getCartItems());
        model.addAttribute("totalPrice", cartService.calculateTotalPrice());
        orderService.placeOrder();

        return "order-confirmation";

    }
}
