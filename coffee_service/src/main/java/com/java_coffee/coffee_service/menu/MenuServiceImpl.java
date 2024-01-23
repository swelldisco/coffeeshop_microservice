package com.java_coffee.coffee_service.menu;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java_coffee.coffee_service.coffee.Coffee;
import com.java_coffee.coffee_service.coffee.CoffeeDto;
import com.java_coffee.coffee_service.coffee.CoffeeMapper;
import com.java_coffee.coffee_service.coffee.constants.CoffeeSize;
import com.java_coffee.coffee_service.coffee.exceptions.CoffeeNotFoundException;

@Service
public class MenuServiceImpl implements MenuService {
    private final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);
    
    @Autowired
    private MenuItems menuItems;
    
    @Autowired
    private MenuRepository repo;
 
    private CoffeeMapper mapper;

    public MenuServiceImpl() {
        this.mapper = new CoffeeMapper();
    }

    @Override
    public void loadMenu() {
        menuItems.loadMenuItems();
        LOGGER.info("Menu Loaded!");
    }

    @Override
    public List<CoffeeDto> getMenu() {
        return repo.findAll().stream()
            .map(c -> mapper.mapToDto(c))
            .toList();
    }

    @Override
    public CoffeeDto getMenuCoffeeById(int coffeeId) {
        return mapper.mapToDto(checkOptionalCoffee(coffeeId));
    }

    @Override
    public List<CoffeeDto> getCoffeesByName(String name) {
        return repo.findAllByDrinkNameIgnoreCase(name).stream()
            .map(c -> mapper.mapToDto(c))
            .toList();
    }

    @Override
    public void addCoffee(CoffeeDto coffeeDto) {
        repo.save(mapper.mapToCoffee(coffeeDto));
        LOGGER.info("Coffee Added");
    }

    @Override
    public void updateCoffee(int coffeeId, CoffeeDto coffeeDto) {
        Coffee updatedCoffee = new Coffee(checkOptionalCoffee(coffeeId));
        updatedCoffee.setSize(CoffeeSize.SHORT);
        updatedCoffee.setBasePrice(coffeeDto.basePrice());
        updatedCoffee.setPrice();
        updatedCoffee.setDrinkName(coffeeDto.drinkName());
        updatedCoffee.setIngredientsList(coffeeDto.ingredientList());
        LOGGER.info("Coffee updated");
    }

    @Override
    public void removeCoffee(int coffeeId) {
        if (repo.existsByCoffeeId(coffeeId)) {
            repo.deleteByCoffeeId(coffeeId);
            LOGGER.info("Coffee removed");
        } else {
            throw new CoffeeNotFoundException();
        }
    }

    private Coffee checkOptionalCoffee(int coffeeId) {
        return repo.findById(coffeeId)
            .orElseThrow(() -> new CoffeeNotFoundException());
    }
    
}
