package com.java_coffee.coffee_service.cartTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.java_coffee.coffee_service.cart.Cart;
import com.java_coffee.coffee_service.cart.CartRepository;
import com.java_coffee.coffee_service.cart.CartServiceImpl;
import com.java_coffee.coffee_service.cart.exceptions.CartNotFoundException;
import com.java_coffee.coffee_service.coffee.Coffee;
import com.java_coffee.coffee_service.coffee.constants.CoffeeSize;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrder;
import com.java_coffee.coffee_service.userStub.UserStub;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TestCartServiceImpl {

    @Mock(name = "database")
    private CartRepository repo;

    @InjectMocks
    private CartServiceImpl service;

    private UserStub testUser1;
    private UserStub testUser2;
    private UserStub testUser3;
    private Cart testCart1;
    private Cart testCart2;
    private Cart testCart3;
    private Coffee testCoffee1;
    private Coffee testCoffee2;
    private Coffee testCoffee3;
    private CoffeeOrder testOrder1;
    private CoffeeOrder testOrder2;
    private CoffeeOrder testOrder3;
    private List<Cart> testList;

    @BeforeEach
    public void setUp() {
        testUser1 = new UserStub(0L, "Billy_Bob");
        testUser2 = new UserStub(1L, "Jim_Bob");
        testUser3 = new UserStub(2L, "Bubba");
        testCart1 = new Cart(0L, testUser1.getUserId(), new ArrayList<CoffeeOrder>(), LocalDateTime.now());
        testCart2 = new Cart(1L, testUser2.getUserId(), new ArrayList<CoffeeOrder>(), LocalDateTime.now());
        testCart3 = new Cart(2L, testUser3.getUserId(), new ArrayList<CoffeeOrder>(), LocalDateTime.now());
        testCoffee1 = new Coffee(0L, CoffeeSize.SHORT, "Test Latte", 1.50, null);
        testCoffee2 = new Coffee(1L, CoffeeSize.GRANDE, "Test Mocha", 1.50, null);
        testCoffee3 = new Coffee(2L, CoffeeSize.TALL, "Test Macchiato", 1.50, null);
        testOrder1 = new CoffeeOrder(testCoffee1, testCart1, 3);
        testOrder2 = new CoffeeOrder(testCoffee2, testCart2, 1);
        testOrder3 = new CoffeeOrder(testCoffee3, testCart3, 5);
        testList = new ArrayList<>();
        testList.add(testCart1);
        testList.add(testCart2);
        testList.add(testCart3);
        
        testCart2.addOrderToCart(testOrder2);
        
    }

    @AfterEach
    public void tearDown() {
        testUser1 = null;
        testUser2 = null;
        testUser3 = null;
        testCart1 = null;
        testCart2 = null;
        testCart3 = null;
        testCoffee1 = null;
        testCoffee2 = null;
        testCoffee3 = null;
        testOrder1 = null;
        testOrder2 = null;
        testOrder3 = null;
        testList = null;
    }
    
    @Test
    public void testCreateCart() {
        // given
        Assertions.assertNotNull(testUser1);
        Assertions.assertNotNull(testCart1);
        Assertions.assertEquals(testCart1.getuserId(), testUser1.getUserId());

        // when
        when(repo.save(any(Cart.class))).thenReturn(testCart1);
        Cart tempCart = service.createCart(testUser1);
        
        // then
        Assertions.assertNotNull(tempCart);
        Assertions.assertEquals(tempCart, testCart1);

    }

    @Test
    public void testGetCardById() {
        // given
        Assertions.assertNotNull(testList);
        int testIndex = 1;
        Cart tempCart = new Cart(testList.get(testIndex));
        Assertions.assertNotNull(tempCart);

        // when
        when(repo.findById((long)testIndex)).thenReturn(testList.stream()
        .filter(c -> c.getCartId() == testIndex)
        .findAny());
        Cart tempCart2 = new Cart(service.getCartById((long)testIndex));

        // then
        Assertions.assertNotNull(tempCart2);
        Assertions.assertEquals(tempCart2.getCartId(), testIndex);
        Assertions.assertEquals(tempCart, tempCart2);
        Assertions.assertThrowsExactly(CartNotFoundException.class, () -> service.getCartById(23859L));
    }

    @Test
    public void testGetCartByUserId() {
        // given
        Assertions.assertNotNull(testList);
        Assertions.assertNotNull(testUser1.getUserId());
        int testId = (int)testUser1.getUserId();
        Assertions.assertEquals(testList.get(testId).getuserId(), testUser1.getUserId());

        // when
        when(repo.findCartByUserId(testId)).thenReturn(testList.stream()
            .filter(c -> c.getuserId() == testId)
            .findFirst());
        Cart tempCart = new Cart(service.getCartByUserId((long)testId));

        // then
        Assertions.assertNotNull(tempCart);
        Assertions.assertEquals(tempCart.getuserId(), testId);
        Assertions.assertEquals(tempCart.getCartId(), testList.get(testId).getCartId());
        Assertions.assertThrowsExactly(CartNotFoundException.class, () -> service.getCartByUserId(32786L));
    }

    @Test
    public void testGetAllCarts() {
        // given
        Assertions.assertNotNull(testList);
        int listSize = testList.size();

        // when
        when(repo.findAll()).thenReturn(testList);
        List<Cart> tempList = service.getAllCarts();

        // then
        Assertions.assertNotNull(tempList);
        Assertions.assertEquals(tempList.size(), listSize);
        Assertions.assertEquals(tempList.get(1), testList.get(1));
    }

    @Test
    public void testAddOrderToCart() {
        // given
        Assertions.assertNotNull(testCart1);
        Assertions.assertNotNull(testOrder1);
        Assertions.assertNotNull(testOrder2);
        Assertions.assertNotNull(testOrder3);

        // when
        when(repo.existsByCartId(testCart1.getCartId())).thenReturn(testList.stream()
        .anyMatch(c -> c.getCartId() == testCart1.getCartId()));
        when(repo.save(testCart1)).thenReturn(testCart1);
        service.addItemToCart(testCart1, testOrder1);
        service.addItemToCart(testCart1, testOrder2);
        service.addItemToCart(testCart1, testOrder3);

        //then
        Assertions.assertNotNull(testCart1.getOrders());
        Assertions.assertEquals(testCart1.getOrders().size(), 3);
        Assertions.assertEquals(testCart1.getOrders().get(1), testOrder2);
    }

    @Test
    public void testRemoveOrderFromCart() {
        // given
        Assertions.assertNotNull(testList);
        Assertions.assertNotNull(testCart2.getOrders());
        Assertions.assertEquals(testCart2.getOrders().size(), 1);
        int testId = (int)testCart2.getCartId();

        // when
        when(repo.existsByCartId((long)testId)).thenReturn(testList.stream()
            .anyMatch(c -> c.getCartId() == testId));
        when(repo.save(testCart2)).thenReturn(testCart2);
        service.removeItemFromCart(testCart2, testOrder2);

        // then
        Assertions.assertNotNull(testCart2.getOrders());
        Assertions.assertEquals(testCart2.getOrders().size(), 0);
    }

    @Test
    public void testClearCart() {
        //given
        Assertions.assertNotNull(testList);
        Assertions.assertNotNull(testCart2.getOrders());
        Assertions.assertEquals(testCart2.getOrders().size(), 1);
        int testId = (int)testCart2.getCartId();

        // when
        when(repo.existsByCartId((long)testId)).thenReturn(testList.stream()
            .anyMatch(c -> c.getCartId() == testId));
        when(repo.save(testCart2)).thenReturn(testCart2);
        service.clearCart(testCart2);

        // then
        Assertions.assertNotNull(testCart2.getOrders());
        Assertions.assertEquals(testCart2.getOrders().size(), 0);
    }

    // wait, this doesn't exist yet
    @Test
    public void testUpdateCart() {
        // given
        Assertions.assertNotNull(testList);
        int testId = 1;
        Cart tempCart = new Cart(testList.get(testId));

        // when
        when(repo.existsByCartId((long)testId)).thenReturn(testList.stream()
            .anyMatch(c -> c.getCartId() == testId));
        when(repo.save(tempCart)).thenReturn(tempCart);
        // then
    }

    @Test
    public void testDeleteCartById() {
        // given
        Assertions.assertNotNull(testList);
        int testId = 1;
        Assertions.assertNotNull(testList.get(testId));

        // when
        when(repo.existsByCartId((long)testId)).thenReturn(testList.stream()
            .anyMatch(c -> c.getCartId() == testId));
        doNothing().when(repo).deleteByCartId((long)testId);
        service.deleteCartById(testId);

        // then
        verify(repo, times(1)).existsByCartId((long)testId);
        verify(repo, times(1)).deleteByCartId((long)testId);
        Assertions.assertThrowsExactly(CartNotFoundException.class, () -> service.deleteCartById(8523L));
    }


}
