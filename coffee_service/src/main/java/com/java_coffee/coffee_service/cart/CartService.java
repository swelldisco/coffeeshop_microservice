package com.java_coffee.coffee_service.cart;

import java.util.List;

import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrder;
import com.java_coffee.coffee_service.userStub.UserStub;

public interface CartService {
    
    Cart createCart(UserStub userStub);
    Cart getCartById(long cartId);
    Cart getCartByUserId(long userId);
    List<Cart> getAllCarts();
    Cart addItemToCart(Cart cart, CoffeeOrder coffeeOrder);
    Cart removeItemFromCart(Cart cart, CoffeeOrder coffeeOrder);
    Cart clearCart(Cart cart);
    void deleteCartById(long cartId);

}
