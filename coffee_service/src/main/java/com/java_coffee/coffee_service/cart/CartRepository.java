package com.java_coffee.coffee_service.cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

public interface CartRepository extends JpaRepository<Cart, Long>{
    boolean existsByCartId(long cartId);
    Optional<Cart> findCartByUserId(long userId);

    @Transactional
    void deleteByCartId(long cartId);
    
}
