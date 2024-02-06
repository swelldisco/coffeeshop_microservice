package com.java_coffee.coffee_service.coffee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java_coffee.coffee_service.exceptions.CoffeeNotFoundException;
import com.java_coffee.coffee_service.mapper.CoffeeMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CoffeeServiceImpl implements CoffeeService {
    
    @Autowired
    private CoffeeMapper mapper;
    
    @Autowired
    private CoffeeRepository repo;

    @Autowired
    private MenuItems menuItems;

    @Override
    public CoffeeDto createCoffee(CoffeeDto coffeeDto) {
        return mapper.mapToCoffeeDto(repo.save(mapper.mapToCoffee(coffeeDto)));
    }

    @Override
    public CoffeeDto findCoffeeById(long coffeeId) {
        return mapper.mapToCoffeeDto(vibrateOptionalCoffee(coffeeId));
    }

    @Override
    public List<CoffeeDto> findAllCoffees() {
        if (repo.findAll() != null && !repo.findAll().isEmpty()) {
            return repo.findAll().stream()
                .map(c -> mapper.mapToCoffeeDto(c))
                .toList();
        } else {
            throw new CoffeeNotFoundException();
        }
    }

    @Override
    public List<CoffeeDto> findAllByName(String name) {
        if (repo.findAllByDrinkNameIgnoringCase(name) != null && !repo.findAllByDrinkNameIgnoringCase(name).isEmpty()) {
            return repo.findAllByDrinkNameIgnoringCase(name).stream()
                .map(c -> mapper.mapToCoffeeDto(c))
                .toList();
        } else {
            throw new CoffeeNotFoundException();
        }
    }

    @Override
    public CoffeeDto updateCoffee(long coffeeId, CoffeeDto coffeeDto) {
        if (repo.existsByCoffeeId(coffeeId)) {
            Coffee updatedCoffee = vibrateOptionalCoffee(coffeeId);
            updatedCoffee.setSize(coffeeDto.size());
            updatedCoffee.setBasePrice(coffeeDto.basePrice());
            updatedCoffee.setPrice();
            updatedCoffee.setDrinkName(coffeeDto.drinkName());
            updatedCoffee.setIngredientsList(coffeeDto.ingredientList());
            return mapper.mapToCoffeeDto(repo.save(updatedCoffee));
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

    @Override
    public void initializeMenu() {
        menuItems.loadMenuItems();
    }

    private Coffee vibrateOptionalCoffee(long coffeeId) {
        return repo.findById(coffeeId)
            .orElseThrow(() -> new CoffeeNotFoundException());
    }
    
}
