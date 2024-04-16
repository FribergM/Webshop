package se.iths.friberg.webshop.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.iths.friberg.webshop.db.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    User findByUsername(String username);
    boolean existsByUsername(String username);

}
