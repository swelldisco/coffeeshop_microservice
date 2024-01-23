package com.java_coffee.coffee_service.menu;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java_coffee.coffee_service.coffee.Coffee;

import jakarta.transaction.Transactional;

@Repository
public interface MenuRepository extends JpaRepository<Coffee, Integer> {
    boolean existsByCoffeeId(int coffeeId);
    List<Coffee> findAllByDrinkNameIgnoreCase(String drinkName);

    @Transactional
    void deleteByCoffeeId(int coffeeId);
}
