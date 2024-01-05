package com.java_coffee.coffee_service.coffee;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java_coffee.coffee_service.coffee.exceptions.CoffeeNotFoundException;

@Service
public class CoffeeServiceImpl implements CoffeeService {
    
    private CoffeeRepository repo;
    private CoffeeMapper mapper;
    
    // for dependency injection so all instances of coffee service use the same hashmap
    public CoffeeServiceImpl() {
    }

    // used by management only
    @Override
    public CoffeeDto createCoffee(CoffeeDto coffeeDto) {
        Coffee newCoffee = mapper.mapToCofee(coffeeDto);
        return mapper.mapToDto(repo.save(newCoffee));
    }

    // used by managment and customer
    @Override
    public CoffeeDto getCoffeeById(int coffeeId) {
        return mapper.mapToDto(tasteTestCoffeeOptional(coffeeId));
    }

    // used by management only
    @Override
    public List<CoffeeDto> getCoffeesByName(String name) {
        if (repo.findAllByNameIgnoreCase(name) != null && !repo.findAllByNameIgnoreCase(name).isEmpty()) {
            return repo.findAllByNameIgnoreCase(name).stream()
                .map(c -> mapper.mapToDto(c))
                .toList();
        } else {
            throw new CoffeeNotFoundException(name);
        }
    }

    // used by management and customer
    @Override
    public List<CoffeeDto> getAllCoffees() {
        if (repo.findAll() != null && !repo.findAll().isEmpty()) {
            return repo.findAll().stream()
                .map(c -> mapper.mapToDto(c))
                .toList();
        } else {
            throw new CoffeeNotFoundException();
        }
    }

    // used by management only
    @Override
    public CoffeeDto updateCoffee(int coffeeId, CoffeeDto coffeeDto) {
        Coffee updatedCoffee = new Coffee(tasteTestCoffeeOptional(coffeeId));
        updatedCoffee.setDrinkName(coffeeDto.getDrinkName());
        updatedCoffee.setBasePrice(coffeeDto.getBasePrice());
        updatedCoffee.setIngredientsList(coffeeDto.getIngredientsList());
        return mapper.mapToDto(repo.save(updatedCoffee));
    }

    // used by management only
    @Override
    public void deleteCoffee(int coffeeId) {
        if (repo.existsById(coffeeId)) {
            repo.deleteById(coffeeId);
        }
    }

    private Coffee tasteTestCoffeeOptional(int coffeeId) {
        return repo.findById(coffeeId)
            .orElseThrow(() -> new CoffeeNotFoundException());
    }

}
