package com.java_coffee.coffee_service.coffeeOrder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
    boolean existsByOrderId(long orderId);
    
}
