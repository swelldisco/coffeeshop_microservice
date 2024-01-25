package com.java_coffee.coffee_service.coffee;

import java.util.List;

public interface CoffeeService {
    
    Coffee createCoffee(Coffee coffee);
    Coffee findCoffeeById(long coffeeId);
    List<Coffee> findAllCoffees();
    List<Coffee> findAllByName(String name);
    Coffee updateCoffee(long coffeeId, Coffee coffee);
    void deleteCoffeeById(long coffeeId);
}
