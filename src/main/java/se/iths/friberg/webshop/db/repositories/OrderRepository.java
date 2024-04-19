package se.iths.friberg.webshop.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.friberg.webshop.db.entities.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findAllByShippedIsTrueOrderByIdDesc();
    List<Order> findAllByShippedIsFalseOrderByIdAsc();
}
