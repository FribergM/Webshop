package se.iths.friberg.webshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import se.iths.friberg.webshop.db.entities.Category;
import se.iths.friberg.webshop.db.entities.Product;
import se.iths.friberg.webshop.db.repositories.CategoryRepository;
import se.iths.friberg.webshop.db.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService{

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    @Autowired
    public ProductService(ProductRepository productRepo, CategoryRepository categoryRepo){
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

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

    public void decreaseStock(Product product, int quantity){
        product.setStock(product.getStock() - quantity);
        productRepo.save(product);
    }

    public void addNewProduct(String productName, Long categoryId, double price, int stock){
        Category category = categoryRepo.findById(categoryId).get();
        productRepo.save(new Product(productName, category, price, stock));
    }

     public List<Category> getAllCategories(){
        return categoryRepo.findAll();
    }
}
