package com.java_coffee.coffee_service.coffee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java_coffee.coffee_service.exceptions.CoffeeNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CoffeeServiceImpl implements CoffeeService {
    
    @Autowired
    private CoffeeRepository repo;

    @Override
    public Coffee createCoffee(Coffee coffee) {
        return repo.save(coffee);
    }

    @Override
    public Coffee findCoffeeById(long coffeeId) {
        return vibrateOptionalCoffee(coffeeId);
    }

    @Override
    public List<Coffee> findAllCoffees() {
        if (repo.findAll() != null && !repo.findAll().isEmpty()) {
            return repo.findAll();
        } else {
            throw new CoffeeNotFoundException();
        }
    }

    @Override
    public List<Coffee> findAllByName(String name) {
        if (repo.findAllByDrinkNameIgnoringCase(name) != null && !repo.findAllByDrinkNameIgnoringCase(name).isEmpty()) {
            return repo.findAllByDrinkNameIgnoringCase(name);
        } else {
            throw new CoffeeNotFoundException();
        }
    }

    @Override
    public Coffee updateCoffee(long coffeeId, Coffee coffee) {
        if (repo.existsByCoffeeId(coffeeId)) {
            Coffee updatedCoffee = vibrateOptionalCoffee(coffeeId);
            updatedCoffee.setSize(coffee.getSize());
            updatedCoffee.setBasePrice(coffee.getBasePrice());
            updatedCoffee.setPrice();
            updatedCoffee.setDrinkName(coffee.getDrinkName());
            updatedCoffee.setIngredientsList(coffee.getIngredientList());
            return repo.save(updatedCoffee);
        } else {
            throw new CoffeeNotFoundException();
        }
    }

    @Override
    public void deleteCoffeeById(long coffeeId) {
        if (repo.existsByCoffeeId(coffeeId)) {
            repo.deleteByCoffeeId(coffeeId);
        } else {
            throw new CoffeeNotFoundException();
        }
    }

    private Coffee vibrateOptionalCoffee(long coffeeId) {
        return repo.findById(coffeeId)
            .orElseThrow(() -> new CoffeeNotFoundException());
    }
    
}
