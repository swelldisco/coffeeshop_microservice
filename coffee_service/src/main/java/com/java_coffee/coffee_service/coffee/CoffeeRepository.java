package com.java_coffee.coffee_service.coffee;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.java_coffee.coffee_service.coffee.constants.CoffeeSize;

import jakarta.transaction.Transactional;

public interface CoffeeRepository extends JpaRepository<Coffee, Long>{
    
    boolean existsByCoffeeId(long coffeeId);
    List<Coffee> findAllByDrinkNameIgnoringCase(String name);

    @Query("SELECT c FROM Coffee c WHERE UPPER(c.drinkName) = UPPER(?1) AND coffeeSize = ?2")
    Optional<Coffee> findCoffeeByDrinkNameAndSize(String name, CoffeeSize coffeeSize);


    @Transactional
    void deleteByCoffeeId(long coffeeId);
}
