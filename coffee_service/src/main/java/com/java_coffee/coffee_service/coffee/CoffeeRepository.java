package com.java_coffee.coffee_service.coffee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;


public interface CoffeeRepository extends JpaRepository<Coffee, Integer>{

    boolean existsById(int id);
    List<Coffee> findAllByNameIgnoreCase(String name);

    @Transactional
    void deleteById(int id);
    
}
