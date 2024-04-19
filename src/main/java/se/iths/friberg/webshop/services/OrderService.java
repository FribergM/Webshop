package se.iths.friberg.webshop.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.friberg.webshop.db.entities.Order;
import se.iths.friberg.webshop.db.entities.OrderItem;
import se.iths.friberg.webshop.db.repositories.OrderRepository;
import se.iths.friberg.webshop.db.repositories.UserRepository;
import se.iths.friberg.webshop.models.CartItem;
import se.iths.friberg.webshop.session.SessionManager;

import java.util.List;

@Service
public class OrderService{

    private final SessionManager sessionManager;
    private final UserRepository userRepo;
    private final OrderRepository orderRepo;
    private final ProductService productService;

    @Autowired
    public OrderService(SessionManager sessionManager, UserRepository userRepo,
                        OrderRepository orderRepo, ProductService productService){
        this.sessionManager = sessionManager;
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
        this.productService = productService;
    }

    @Transactional
    public void placeOrder(){
        Order order = constructOrder();

        saveOrder(order);
    }

    private Order constructOrder(){
        List<CartItem> cartItems = sessionManager.getShoppingCart().getCartItems();
        Order order = new Order(sessionManager.getUser());

        for(CartItem item : cartItems){
            order.getOrderItems().add(new OrderItem(
                    order,
                    item.getProduct(),
                    item.getQuantity())
            );
            productService.decreaseStock(item.getProduct(),item.getQuantity());
        }
        return order;
    }

    private void saveOrder(Order order){
        sessionManager.getUser().getOrders().add(order);
        userRepo.save(sessionManager.getUser());
        sessionManager.clearShoppingCart();
    }

    public List<Order> getPendingOrders(){
        return orderRepo.findAllByShippedIsFalseOrderByIdAsc();
    }
    public List<Order> getShippedOrders(){
        return orderRepo.findAllByShippedIsTrueOrderByIdDesc();
    }

    @Transactional
    public void updateOrderStatus(List<Long> orderIds){
        Order shippedOrder;
        for(Long id : orderIds){
            shippedOrder = orderRepo.findById(id).get();
            shippedOrder.setShipped(true);
            orderRepo.save(shippedOrder);
        }
    }
}
