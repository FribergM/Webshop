package se.iths.friberg.webshop.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.iths.friberg.webshop.db.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{

}
