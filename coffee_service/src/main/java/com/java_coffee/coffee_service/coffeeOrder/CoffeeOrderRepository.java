package com.java_coffee.coffee_service.coffeeOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
    boolean existsByOrderId(long orderId);
    
    //@Query(value = "SELECT o FROM CoffeeOrder o WHERE o.cartId = ?1")
    List<CoffeeOrder> findByCartId(long cartId);
    List<CoffeeOrder> findByUserId(long userId);

    Optional<CoffeeOrder> findFirstByCartId(long cartId);

    @Query("SELECT o FROM CoffeeOrder o WHERE (o.cartId = ?1) AND (o.isPaid = false)")
    List<CoffeeOrder> findUnpaidOrderByCartId(long cartId);

    @Transactional
    void deleteCoffeeOrderByOrderId(long orderId);
    
}
