package se.iths.friberg.webshop.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import se.iths.friberg.webshop.db.entities.Category;
import se.iths.friberg.webshop.db.entities.Product;
import se.iths.friberg.webshop.db.repositories.ProductRepository;
import se.iths.friberg.webshop.models.CartItem;
import se.iths.friberg.webshop.models.ShoppingCart;
import se.iths.friberg.webshop.session.SessionManager;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartServiceTest{

    @Spy
    private SessionManager mockSessionManager;
    @Mock
    private ProductRepository mockRepo;
    @InjectMocks
    private ShoppingCartService mockCartService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mockSessionManager.setShoppingCart(new ShoppingCart());
    }

    @Test
    void findAndAddToCart(){
        Long productId = 1L;
        int quantity = 5;
        Product product = new Product("Test",new Category(),10,50);
        when(mockRepo.findById(productId)).thenReturn(Optional.of(product));

        String result = mockCartService.findAndAddToCart(productId,quantity);

        assertEquals(1, mockSessionManager.getShoppingCart().getCartSize());
        assertEquals("5x Test added to cart.", result);
    }

    @Test
    void changeItemQuantity(){
        CartItem cartItem = new CartItem(new Product(), 5);
        mockSessionManager.getShoppingCart().addToCart(cartItem);
        int itemIndex = 0;

        mockCartService.changeItemQuantity("increase",itemIndex);

        assertEquals(6,mockSessionManager.getShoppingCart().getItemQuantity(itemIndex));
        mockCartService.changeItemQuantity("decrease",itemIndex);
        assertEquals(5,mockSessionManager.getShoppingCart().getItemQuantity(itemIndex));
    }

    @Test
    void calculateTotalPrice(){
        Product product = new Product("Test product",new Category(),10,0);
        mockSessionManager.getShoppingCart().addToCart(new CartItem(product,10));
        mockSessionManager.getShoppingCart().addToCart(new CartItem(product,10));

        double totalPrice = mockCartService.calculateTotalPrice();

        assertEquals(200,totalPrice);
    }
}