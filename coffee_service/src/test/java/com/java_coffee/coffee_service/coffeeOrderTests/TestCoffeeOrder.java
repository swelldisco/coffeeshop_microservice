package com.java_coffee.coffee_service.coffeeOrderTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.java_coffee.coffee_service.cart.Cart;
import com.java_coffee.coffee_service.coffee.Coffee;
import com.java_coffee.coffee_service.coffee.constants.CoffeeSize;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrder;
import com.java_coffee.coffee_service.pojo.UserStub;

public class TestCoffeeOrder {
    
    private UserStub testUser;
    private Coffee testCoffee;
    private Cart testCart;
    private CoffeeOrder testCoffeeOrder;

    @BeforeEach
    public void setUp() {
        testUser = new UserStub(0L, "Billy_Bob", "bb@gmail.com");
        testCoffee = new Coffee(0L, CoffeeSize.GRANDE, "Test Latte", 2.50, null);
        testCart = new Cart(testUser);
    }

    @AfterEach
    public void tearDown() {
        testUser = null;
        testCoffee = null;
        testCart = null;
        testCoffeeOrder = null;
    }

    @Test
    public void testCreateCoffeeOrder() {
        Assertions.assertNotNull(testUser);
        Assertions.assertNotNull(testCoffee);
        Assertions.assertNotNull(testCart);

        testCoffeeOrder = new CoffeeOrder(testCoffee, testCart, 1);

        Assertions.assertNotNull(testCoffeeOrder);
        Assertions.assertEquals(testCoffeeOrder.getCoffee(), testCoffee);
        Assertions.assertEquals(testCoffeeOrder.getCartId(), testCart.getCartId());
        Assertions.assertEquals(testCoffeeOrder.getQuantity(), 1);
    }


}
