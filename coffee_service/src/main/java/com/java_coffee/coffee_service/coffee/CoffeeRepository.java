package com.java_coffee.coffee_service.coffee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

public interface CoffeeRepository extends JpaRepository<Coffee, Long>{
    
    boolean existsByCoffeeId(long coffeeId);
    List<Coffee> findAllByDrinkNameIgnoringCase(String name);

    @Transactional
    void deleteByCoffeeId(long coffeeId);
}
