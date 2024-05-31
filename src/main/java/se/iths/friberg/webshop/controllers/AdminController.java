package se.iths.friberg.webshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.friberg.webshop.services.ModelService;
import se.iths.friberg.webshop.services.OrderService;
import se.iths.friberg.webshop.services.ProductService;
import se.iths.friberg.webshop.session.SessionManager;

import java.util.List;

@Controller
public class AdminController{

    private final SessionManager sessionManager;
    private final ModelService modelService;
    private final OrderService orderService;
    private final ProductService productService;

    @Autowired
    public AdminController(SessionManager sessionManager, ModelService modelService,
                           OrderService orderService, ProductService productService){
        this.sessionManager = sessionManager;
        this.modelService = modelService;
        this.orderService = orderService;
        this.productService = productService;
    }
    
    @GetMapping("/admin")
    public String adminPage(Model model){
        modelService.addHeaderAttributes(model);

        return "admin-menu";
    }

    @GetMapping("/admin/orders/shipped")
    public String viewShippedOrders(Model model){
        modelService.addHeaderAttributes(model);
        model.addAttribute("orders",orderService.getShippedOrders());

        return "admin-shipped-orders";
    }

    @GetMapping("/admin/orders/pending")
    public String viewPendingOrders(Model model){
        modelService.addHeaderAttributes(model);
        model.addAttribute("orders",orderService.getPendingOrders());

        return "admin-pending-orders";
    }

    @PostMapping("/admin/orders/update")
    public String managePendingOrders(@RequestParam(value = "orderId", required = false) List<Long> orderIds,
                                      Model model){
        modelService.addHeaderAttributes(model);

        if(orderIds != null){
            orderService.updateOrderStatus(orderIds);
        }
        return "redirect:/admin/orders/pending";
    }

    @GetMapping("/admin/create-product")
    public String addProduct(Model model){
        modelService.addHeaderAttributes(model);
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("addPrompt","");

        return "admin-add-product";
    }
    @PostMapping("/admin/add-product")
    public String addProductPost(@RequestParam String productName,
                                @RequestParam Long categoryId,
                                @RequestParam double price,
                                @RequestParam int stock,
                                Model model){
        modelService.addHeaderAttributes(model);

        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("addPrompt","'" + productName + "' has been added to the catalog.");

        productService.addNewProduct(productName, categoryId, price, stock);

        return "admin-add-product";
    }
}
