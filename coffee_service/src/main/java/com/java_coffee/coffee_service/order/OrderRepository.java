package com.java_coffee.coffee_service.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

public interface OrderRepository extends JpaRepository<Order, Long>{
    
    boolean existsByOrderId(long orderId);
    List<Order> findAllByUserId(long userId);
    @Transactional
    void deleteByOrderId(long orderId);
    
}
