package com.java_coffee.coffee_service.coffee;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java_coffee.coffee_service.coffee.constants.CoffeeSize;
import com.java_coffee.coffee_service.exceptions.CoffeeNotFoundException;
import com.java_coffee.coffee_service.exceptions.NoSuchSizeException;
import com.java_coffee.coffee_service.exceptions.RepositoryEmptyException;
import com.java_coffee.coffee_service.mapper.CoffeeMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CoffeeServiceImpl implements CoffeeService {
    
    @Autowired
    private CoffeeMapper mapper;
    
    @Autowired
    private CoffeeRepository repo;

    private MenuItems menuItems;

    // barista and admin only
    @Override
    public CoffeeDto createCoffee(CoffeeDto coffeeDto) {
        Coffee coffee = mapper.mapToCoffee(coffeeDto);
        coffee.setPrice();
        return mapper.mapToCoffeeDto(repo.save(coffee));
    }

    // everyone
    @Override
    public CoffeeDto findCoffeeById(long coffeeId) {
        return mapper.mapToCoffeeDto(vibrateOptionalCoffee(coffeeId));
    }

    // to get a specific coffee out of the database to add to the user's order
    @Override
    public CoffeeDto findCoffeeByNameAndSize(String drinkName, String size) {
        return mapper.mapToCoffeeDto(vibrateOptionalCoffee(drinkName, parseStringToSize(size)));
    }

    // everyone
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

    // everyone
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

    // admin and barista only
    @Override
    public CoffeeDto updateCoffee(long coffeeId, CoffeeDto coffeeDto) {
        if (repo.existsByCoffeeId(coffeeId)) {
            Coffee updatedCoffee = vibrateOptionalCoffee(coffeeId);
            updatedCoffee.setDrinkName(coffeeDto.drinkName());
            updatedCoffee.setSize(coffeeDto.coffeeSize());
            updatedCoffee.setBasePrice(coffeeDto.basePrice());
            updatedCoffee.setPrice();
            updatedCoffee.setIngredientsList(coffeeDto.ingredientList());
            return mapper.mapToCoffeeDto(repo.save(updatedCoffee));
        } else {
            throw new CoffeeNotFoundException();
        }
    }

    // admin and maybe barista only
    @Override
    public void deleteCoffeeById(long coffeeId) {
        if (repo.existsByCoffeeId(coffeeId)) {
            repo.deleteByCoffeeId(coffeeId);
        } else {
            throw new CoffeeNotFoundException();
        }
    }

    // to dump the menu into the coffee table of the database since we don't allow users to 'create' coffees, only order them
    // admin and (maybe) barista only
    @Override
    public void initializeMenu() {
        List<Coffee> menu = menuItems.createLists();
        for (Coffee coffee : menu) {
            repo.save(coffee);
        }
    }

    // to present the menu in a sane manner as the user would expect in a proper coffee shop since it looks like React won't allow me to do this from an unfiltered list in the front end.
    // this is ugly, and probably has a bad code smell.  I need a SQL wizard to help me figure out how to make Spring JPA do this query in the repository rather than filtering the coffee list here.
    @Override
    public List<MenuItemDto> getMenu() {
        List<Coffee> tempList = repo.findAll();
        if (tempList != null && !tempList.isEmpty()) {
            Set<String> tempSet = new HashSet<>();
            List<MenuItemDto> menu = new ArrayList<>();
            for (Coffee coffee : tempList) {
                if (tempSet.add(coffee.getDrinkName())) {
                    menu.add(new MenuItemDto(
                        coffee.getCoffeeId(),
                        coffee.getDrinkName(), 
                        String.format("%.2f", coffee.getBasePrice()), 
                        String.format("%.2f", coffee.calculateUpcharge(coffee.getBasePrice(), 
                        CoffeeSize.TALL)), 
                        String.format("%.2f", coffee.calculateUpcharge(coffee.getBasePrice(), 
                        CoffeeSize.GRANDE)), 
                        String.format("%.2f", coffee.calculateUpcharge(coffee.getBasePrice(), CoffeeSize.VENTI))));
                }
            }
            return menu;
        } else {
            throw new RepositoryEmptyException();
        }
    }

    // checking optionals
    private Coffee vibrateOptionalCoffee(long coffeeId) {
        return repo.findById(coffeeId)
            .orElseThrow(() -> new CoffeeNotFoundException());
    }

    private Coffee vibrateOptionalCoffee(String coffeeName, CoffeeSize coffeeSize) {
        return repo.findCoffeeByDrinkNameAndSize(coffeeName, coffeeSize)
            .orElseThrow(() -> new CoffeeNotFoundException());
    }

    // to convert from string to CoffeeSize for flexibility
    private CoffeeSize parseStringToSize(String size) {
        switch (size.toLowerCase()) {
            case "short": return CoffeeSize.SHORT;
            case "tall": return CoffeeSize.TALL;
            case "grande": return CoffeeSize.GRANDE;
            case "venti": return CoffeeSize.VENTI;
            default: throw new NoSuchSizeException();
        }
    }
    
}
