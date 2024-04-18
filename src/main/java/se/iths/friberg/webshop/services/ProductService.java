package se.iths.friberg.webshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import se.iths.friberg.webshop.db.entities.Product;
import se.iths.friberg.webshop.db.repositories.ProductRepository;

import java.util.Optional;

@Service
public class ProductService{

    @Autowired
    ProductRepository productRepo;

    public Long queryProduct(String query){
        Optional<Product> product = productRepo.findByProductName(query);

        if(product.isPresent()){
            return product.get().getId();
        }else{
            return null;
        }

    }

    public void queryAndAddProductToModel(Long id, Model model){
        Optional<Product> product = productRepo.findById(id);

        if(product.isPresent()){
            model.addAttribute("productFound", true);
            model.addAttribute("product",product.get());
        }else{
            model.addAttribute("productFound",false);
        }
    }
}
