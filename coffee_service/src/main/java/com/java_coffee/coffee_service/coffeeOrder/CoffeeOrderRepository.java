package com.java_coffee.coffee_service.coffeeOrder;


import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import java.util.List;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
    boolean existsByOrderId(long orderId);
    List<CoffeeOrder> findByCartId(long cartId);
    List<CoffeeOrder> findByUserId(long userId);

    @Transactional
    void deleteCoffeeOrderByOrderId(long orderId);
    
}
