package com.java_coffee.coffee_service.cartTests;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.java_coffee.coffee_service.cart.Cart;
import com.java_coffee.coffee_service.coffee.Coffee;
import com.java_coffee.coffee_service.coffee.constants.CoffeeSize;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrder;
import com.java_coffee.coffee_service.userStub.UserStub;

public class TestCart {
    
    private UserStub testUser;
    private Coffee testCoffee1;
    private Coffee testCoffee2;
    private Cart testCart1;
    private Cart testCart2;
    private CoffeeOrder testOrder1;
    private CoffeeOrder testOrder2;

    @BeforeEach
    public void setUp() {
        testUser = new UserStub(0L, "Billy_Bob");
        testCoffee1 = new Coffee(0L, CoffeeSize.GRANDE, "Test Latte", 2.50, null);
        testCoffee2 = new Coffee(1L, CoffeeSize.SHORT, "Test Mocha", 3.00, null);
        testCart1 = new Cart(testUser);
        testCart2 = new Cart(testUser);
        testOrder1 = new CoffeeOrder(testCoffee1, testCart2, 1);
        testOrder2 = new CoffeeOrder(testCoffee2, testCart2, 1);
        testCart2.addOrderToCart(testOrder1);
        testCart2.addOrderToCart(testOrder2);
    }

    @AfterEach
    public void tearDown() {
        testUser = null;
        testCoffee1 = null;
        testCoffee2 = null;
        testCart1 = null;
        testCart2 = null;
        testOrder1 = null;
        testOrder2 = null;
    }

    @Test
    public void testCreateCart() {
        Assertions.assertNotNull(testUser);
        Cart tempCart = new Cart(testUser);

        Assertions.assertNotNull(tempCart);
        Assertions.assertEquals(tempCart.getuserId(), testUser.getUserId());
        //Assertions.assertNotNull(tempCart.getTimeStamp());
    }

    @Test
    public void testAddCoffeeOrderToCart() {
        Assertions.assertNotNull(testCart1);
        Assertions.assertNotNull(testCoffee1);
        Assertions.assertNotNull(testCoffee2);
        CoffeeOrder tempOrder1 = new CoffeeOrder(testCoffee1, testCart1, 1);
        Assertions.assertNotNull(tempOrder1);
        CoffeeOrder tempOrder2 = new CoffeeOrder(testCoffee2, testCart1, 1);
        Assertions.assertNotNull(tempOrder2);

        testCart1.addOrderToCart(tempOrder1);
        testCart1.addOrderToCart(tempOrder2);

        Assertions.assertNotNull(testCart1.getOrders());
        Assertions.assertEquals(testCart1.getOrders().size(), 2);

        List<CoffeeOrder> tempList = testCart1.getOrders();
        Assertions.assertEquals(tempOrder1, tempList.get(0));
        Assertions.assertEquals(tempOrder2, tempList.get(1));
    }

    @Test
    public void testRemoveOrderFromCart() {
        Assertions.assertNotNull(testCart2);
        Assertions.assertNotNull(testOrder1);
        Assertions.assertNotNull(testCart2.getOrders());
        Assertions.assertEquals(testCart2.getOrders().size(), 2);
        Assertions.assertTrue(testCart2.getOrders().contains(testOrder1));

        testCart2.removeOrderFromCart(testOrder1);

        Assertions.assertNotNull(testCart2.getOrders());
        Assertions.assertEquals(testCart2.getOrders().size(), 1);
        Assertions.assertFalse(testCart2.getOrders().contains(testOrder1));
    }

    @Test
    public void testEmptyCart() {
        Assertions.assertNotNull(testCart2);
        Assertions.assertNotNull(testCart2.getOrders());
        Assertions.assertEquals(testCart2.getOrders().size(), 2);

        testCart2.emptyCart();

        Assertions.assertNotNull(testCart2.getOrders());
        Assertions.assertEquals(testCart2.getOrders().size(), 0);
    }


 }
