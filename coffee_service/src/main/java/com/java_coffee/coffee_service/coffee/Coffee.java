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
@Table(name = "coffee")
public class Coffee{

    @Id
    @Column(name = "coffee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
    private List<String> ingredientsList;

    protected Coffee() {}

    public Coffee(String drinkName, double basePrice, List<String> ingredientsList) {
        this.size = CoffeeSize.SHORT;
        this.drinkName = drinkName;
        this.basePrice = basePrice;
        this.price = calculateUpcharge(basePrice, size);
        this.ingredientsList = ingredientsList;
    }

    protected Coffee(Coffee source) {
        this.id = source.id;
        this.size = source.size;
        this.drinkName = source.drinkName;
        this.basePrice = source.basePrice;
        this.price = calculateUpcharge(basePrice, size);
        this.ingredientsList = source.ingredientsList;
    }

    protected Coffee(int id, CoffeeSize size, String drinkName, double basePrice, List<String> ingredientList) {
        this.id = id;
        this.size = size;
        this.drinkName = drinkName;
        this.basePrice = basePrice;
        this.price = calculateUpcharge(basePrice, size);
        this.ingredientsList= ingredientList;
    }

    protected double calculateUpcharge(double basePrice, CoffeeSize size) {
        switch (size) {
            case SHORT: return basePrice;
            case TALL: return basePrice + 0.30;
            case GRANDE: return basePrice + 0.65;
            case VENTI: return basePrice + 1.00;
            default: return 0.00;
        }
    }

    protected int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    protected CoffeeSize getSize() {
        return size;
    }

    protected void setSize(CoffeeSize size) {
        this.size = size;
    }

    protected String getDrinkName() {
        return drinkName;
    }

    protected void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    protected double getPrice() {
        return price;
    }

    protected void setPrice() {
        this.price = calculateUpcharge(basePrice, size);
    }

    protected double getBasePrice() {
        return basePrice;
    }

    protected void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    protected List<String> getIngredientsList() {
        return ingredientsList;
    }

    protected void setIngredientsList(List<String> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    protected String readOutIngredients() {
        if (ingredientsList != null && !ingredientsList.isEmpty()) {
            return ingredientsList.stream()
                .collect(Collectors.joining(", "));
        } else {
            return "No ingredients listed.";
        }
    }

    @Override
    public String toString() {
        return (getId()+1) + ": " + getSize() + " " + getDrinkName() + " $" + String.format("%.2f", getPrice());
    }

    // just to remind myself
    // @Override
    // public int compareTo(Coffee coffee) {
    //     return Double.compare(this.getPrice(), coffee.getBasePrice());
    // }
    
}
