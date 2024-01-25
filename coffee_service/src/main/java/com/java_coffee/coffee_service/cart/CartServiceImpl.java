package com.java_coffee.coffee_service.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java_coffee.coffee_service.cart.exceptions.CartNotFoundException;
import com.java_coffee.coffee_service.coffeeOrder.CoffeeOrder;
import com.java_coffee.coffee_service.userStub.UserStub;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository repo;

    @Override
    public Cart createCart(UserStub userStub) {
        Cart cart = new Cart(userStub);
        return repo.save(cart);
    }

    @Override
    public Cart getCartById(long cartId) {
        return digThroughOptionalCart(cartId);
    }

    @Override
    public Cart getCartByUserId(long userId) {
        return rudelyInvestigateAUsersCart(userId);
    }

    @Override
    public List<Cart> getAllCarts() {
        if (repo.findAll() != null && !repo.findAll().isEmpty()) {
            return repo.findAll();
        } else {
            throw new CartNotFoundException();
        }
    }

    @Override
    public Cart addItemToCart(Cart cart, CoffeeOrder coffeeOrder) {
        if (repo.existsByCartId(cart.getCartId())) {
            cart.addOrderToCart(coffeeOrder);
            return repo.save(cart);
        } else {
            throw new CartNotFoundException();
        }
    }

    @Override
    public Cart removeItemFromCart(Cart cart, CoffeeOrder coffeeOrder) {
        if (repo.existsByCartId(cart.getCartId())) {
            cart.removeOrderFromCart(coffeeOrder);
            return repo.save(cart);
        } else {
            throw new CartNotFoundException();
        }
    }

    @Override
    public Cart clearCart(Cart cart) {
        if (repo.existsByCartId(cart.getCartId())) {
            cart.emptyCart();
            return repo.save(cart);
        } else {
            throw new CartNotFoundException();
        }
    }

    @Override
    public void deleteCartById(long cartId) {
        if (repo.existsByCartId(cartId)) {
            repo.deleteByCartId(cartId);
        } else {
            throw new CartNotFoundException();
        }
    }

    private Cart digThroughOptionalCart(long cartId) {
        return repo.findById(cartId)
            .orElseThrow(() -> new CartNotFoundException());
    }

    private Cart rudelyInvestigateAUsersCart(long userId) {
        return repo.findCartByUserId(userId)
            .orElseThrow(() -> new CartNotFoundException());
    }
    
}
