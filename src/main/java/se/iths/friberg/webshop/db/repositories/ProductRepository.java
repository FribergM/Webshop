package se.iths.friberg.webshop.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.iths.friberg.webshop.db.entities.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
    @Query("SELECT p FROM Product p JOIN p.category c WHERE c.categoryName = :categoryName")
    List<Product> findAllByCategoryName(String categoryName);
    Optional<Product> findByProductName(String productName);
    List<Product> findAllByProductNameContaining(String name);
}
