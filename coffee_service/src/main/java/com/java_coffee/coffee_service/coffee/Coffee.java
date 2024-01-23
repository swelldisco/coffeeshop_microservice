package com.java_coffee.coffee_service.coffee;

import java.util.List;
import java.util.stream.Collectors;

import com.java_coffee.coffee_service.coffee.constants.CoffeeSize;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
// @Table(name = "coffee")
// @SecondaryTable(name = "menu")
@Table(name = "menu")
public class Coffee{

    @Id
    @Column(name = "coffee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int coffeeId;

    @Column(name = "size")
    private CoffeeSize size;

    @Column(name = "drink_name", nullable = false, length = 50, unique = true)
    private String drinkName;

    @Column(name = "base_price", nullable = false, length = 7)
    private double basePrice;

    @Column(name = "price")
    private double price;

    @Column(name = "ingredient_list")
    @ElementCollection
    private List<String> ingredientList;

    protected Coffee() {}

    public Coffee(String drinkName, double basePrice, List<String> ingredientList) {
        this.size = CoffeeSize.SHORT;
        this.drinkName = drinkName;
        this.basePrice = basePrice;
        this.price = calculateUpcharge(basePrice, size);
        this.ingredientList = ingredientList;
    }

    public Coffee(Coffee source) {
        this.coffeeId = source.coffeeId;
        this.size = source.size;
        this.drinkName = source.drinkName;
        this.basePrice = source.basePrice;
        this.price = calculateUpcharge(basePrice, size);
        this.ingredientList = source.ingredientList;
    }

    public Coffee(int coffeeId, CoffeeSize size, String drinkName, double basePrice, List<String> ingredientList) {
        this.coffeeId = coffeeId;
        this.size = size;
        this.drinkName = drinkName;
        this.basePrice = basePrice;
        this.price = calculateUpcharge(basePrice, size);
        this.ingredientList= ingredientList;
    }

    public double calculateUpcharge(double basePrice, CoffeeSize size) {
        switch (size) {
            case SHORT: return basePrice;
            case TALL: return basePrice + 0.30;
            case GRANDE: return basePrice + 0.65;
            case VENTI: return basePrice + 1.00;
            default: return 0.00;
        }
    }

    public int getCoffeeId() {
        return coffeeId;
    }

    public void setCoffeeId(int coffeeId) {
        this.coffeeId = coffeeId;
    }

    public CoffeeSize getSize() {
        return size;
    }

    public void setSize(CoffeeSize size) {
        this.size = size;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice() {
        this.price = calculateUpcharge(basePrice, size);
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public List<String> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientsList(List<String> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public String readOutIngredients() {
        if (ingredientList != null && !ingredientList.isEmpty()) {
            return ingredientList.stream()
                .collect(Collectors.joining(", "));
        } else {
            return "No ingredients listed.";
        }
    }

    @Override
    public String toString() {
        return getCoffeeId() + ": " + getSize() + " " + getDrinkName() + " $" + String.format("%.2f", getPrice());
    }

    // just to remind myself
    // @Override
    // public int compareTo(Coffee coffee) {
    //     return Double.compare(this.getPrice(), coffee.getBasePrice());
    // }
    
}
