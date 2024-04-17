package se.iths.friberg.webshop.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import se.iths.friberg.webshop.db.entities.Category;
import se.iths.friberg.webshop.db.entities.Product;
import se.iths.friberg.webshop.db.repositories.CategoryRepository;
import se.iths.friberg.webshop.db.repositories.ProductRepository;
import se.iths.friberg.webshop.services.ProductService;
import se.iths.friberg.webshop.session.SessionDetails;

import java.util.List;

@Controller
public class CatalogController{

    @Autowired
    SessionDetails sessionDetails;
    @Autowired
    CategoryRepository categoryRepo;
    @Autowired
    ProductRepository productRepo;
    @Autowired
    ProductService productService;

    //TODO THIS
//    @PostMapping("/search")
//    public String productSearch(@RequestParam String query, Model model){
//
//        model.addAttribute("query", query);
//
//    }

    @GetMapping(value = {"/","/categories"})
    public String homePage(Model model){
        model.addAttribute("loggedIn", sessionDetails.isLoggedIn());

        List<Category> categories = categoryRepo.findAll();

        model.addAttribute("categories", categories);
        return "categories";
    }
    @GetMapping("/category/{categoryName}")
    public String category(@PathVariable String categoryName, Model model){
        model.addAttribute("loggedIn", sessionDetails.isLoggedIn());

        List<Product> products = productRepo.findAllByCategoryName(categoryName);
        model.addAttribute("products",products);

        return "products";
    }
    @GetMapping("/product/{productId}")
    public String product(@PathVariable Long productId, Model model){
        model.addAttribute("loggedIn", sessionDetails.isLoggedIn());

        //TODO Improve this in the future with list of results with '.contains()'
        productService.queryAndAddProductToModel(productId, model);

        return "productPage";
    }
}
