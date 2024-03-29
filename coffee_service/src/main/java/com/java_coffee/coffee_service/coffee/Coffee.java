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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "coffee")
public class Coffee{

    @Id
    @Column(name = "coffee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long coffeeId;

    // somehow this enum is causing a null pointer exception in postman
    @Column(name = "coffee_size")
    private CoffeeSize coffeeSize;

    @Column(name = "drink_name", nullable = false, length = 50)
    @NotBlank(message = "Drink name cannot be left blank.")
    @NotEmpty(message = "Drink name cannot be left empty.")
    @NotNull(message = "Drink name cannot be left null.")
    private String drinkName;

    @Column(name = "base_price")
    @NotNull(message = "All drinks must have a base price.")
    private double basePrice;

    @Column(name = "price")
    private double price;

    @Column(name = "ingredient_list")
    @ElementCollection
    private List<String> ingredientList;

    protected Coffee() {}

    public Coffee(CoffeeSize coffeeSize, String drinkName, double basePrice, List<String> ingredientList) {
        this.coffeeSize = coffeeSize;
        this.drinkName = drinkName;
        this.basePrice = basePrice;
        this.price = 0.00;
        this.ingredientList = ingredientList;
    }

    public Coffee(Coffee source) {
        this.coffeeId = source.coffeeId;
        this.coffeeSize = source.coffeeSize;
        this.drinkName = source.drinkName;
        this.basePrice = source.basePrice;
        this.price = source.basePrice;
        this.ingredientList = source.ingredientList;
    }

    public Coffee(long coffeeId, CoffeeSize coffeeSize, String drinkName, double basePrice, List<String> ingredientList) {
        this.coffeeId = coffeeId;
        this.coffeeSize = coffeeSize;
        this.drinkName = drinkName;
        this.basePrice = basePrice;
        this.price = calculateUpcharge(basePrice, coffeeSize);
        this.ingredientList= ingredientList;
    }

    public double calculateUpcharge(double basePrice, CoffeeSize coffeeSize) {
        switch (coffeeSize) {
            case SHORT: return basePrice;
            case TALL: return basePrice += 0.30;
            case GRANDE: return basePrice += 0.65;
            case VENTI: return basePrice += 1.00;
            default: return 0.00;
        }
    }

    public long getCoffeeId() {
        return coffeeId;
    }

    public void setCoffeeId(int coffeeId) {
        this.coffeeId = coffeeId;
    }

    public CoffeeSize getSize() {
        return coffeeSize;
    }

    public void setSize(CoffeeSize coffeeSize) {
        this.coffeeSize = coffeeSize;
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
        this.price = calculateUpcharge(basePrice, coffeeSize);
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((coffeeSize == null) ? 0 : coffeeSize.hashCode());
        result = prime * result + ((drinkName == null) ? 0 : drinkName.hashCode());
        long temp;
        temp = Double.doubleToLongBits(basePrice);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(price);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((ingredientList == null) ? 0 : ingredientList.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Coffee other = (Coffee) obj;
        if (coffeeSize != other.coffeeSize)
            return false;
        if (drinkName == null) {
            if (other.drinkName != null)
                return false;
        } else if (!drinkName.equals(other.drinkName))
            return false;
        if (Double.doubleToLongBits(basePrice) != Double.doubleToLongBits(other.basePrice))
            return false;
        if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
            return false;
        if (ingredientList == null) {
            if (other.ingredientList != null)
                return false;
        } else if (!ingredientList.equals(other.ingredientList))
            return false;
        return true;
    }

    // just to remind myself
    // @Override
    // public int compareTo(Coffee coffee) {
    //     return Double.compare(this.getPrice(), coffee.getBasePrice());
    // }

    
    
}
