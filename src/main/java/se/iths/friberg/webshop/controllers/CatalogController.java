package se.iths.friberg.webshop.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.friberg.webshop.db.entities.Category;
import se.iths.friberg.webshop.db.entities.Product;
import se.iths.friberg.webshop.db.repositories.CategoryRepository;
import se.iths.friberg.webshop.db.repositories.ProductRepository;
import se.iths.friberg.webshop.services.ModelService;
import se.iths.friberg.webshop.services.ProductService;
import se.iths.friberg.webshop.services.ShoppingCartService;

import java.util.List;

@Controller
public class CatalogController{

    private final CategoryRepository categoryRepo;
    private final ProductRepository productRepo;
    private final ProductService productService;
    private final ModelService modelService;
    private final ShoppingCartService cartService;

    @Autowired
    public CatalogController(CategoryRepository categoryRepo,
                             ProductRepository productRepo, ProductService productService,
                             ModelService modelService, ShoppingCartService cartService){
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
        this.productService = productService;
        this.modelService = modelService;
        this.cartService = cartService;
    }

    @GetMapping(value = {"/","/categories"})
    public String homePage(Model model){
        modelService.addHeaderAttributes(model);

        List<Category> categories = categoryRepo.findAll();

        model.addAttribute("categories", categories);
        return "categories";
    }
    @GetMapping("/category/{categoryName}")
    public String category(@PathVariable String categoryName, Model model){
        modelService.addHeaderAttributes(model);

        List<Product> products = productRepo.findAllByCategoryName(categoryName);
        model.addAttribute("products",products);

        return "products";
    }
    @GetMapping("/product/{productId}")
    public String product(@PathVariable Long productId, Model model){
        modelService.addHeaderAttributes(model);

        //TODO Improve this in the future with list of results with '.contains()'
        productService.queryAndAddProductToModel(productId, model);

        return "product-details";
    }
    @PostMapping("/product/{productId}")
    public String productPost(@RequestParam int quantity,
                              @PathVariable Long productId,
                              Model model){
        modelService.addHeaderAttributes(model);

        //TODO Improve this in the future with list of results with '.contains()'
        productService.queryAndAddProductToModel(productId, model);


        String orderPrompt = cartService.findAndAddToCart(productId,quantity);
        model.addAttribute("orderPrompt", orderPrompt);
        return "product-details";
    }
    @PostMapping("/search")
    public String productSearch(@RequestParam(value = "search") String query, Model model){
        modelService.addHeaderAttributes(model);

        Long productId = productService.queryProduct(query);

        if(productId != null){
            return "redirect:/product/" + productId;
        }
        return "no-search-match";

    }
}
