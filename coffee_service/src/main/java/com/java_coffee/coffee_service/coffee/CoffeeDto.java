package com.java_coffee.coffee_service.coffee;
import com.java_coffee.coffee_service.coffee.constants.CoffeeSize;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CoffeeDto {
    
    private int id;

    @NotNull(message = "Size cannot be left null.")
    @NotBlank(message = "Size cannot be left blank.")
    @NotEmpty(message = "Size cannot be left empty.")
    private CoffeeSize size;

    @NotNull(message = "Drink name cannot be left null.")
    @NotBlank(message = "Drink name cannot be left blank.")
    @NotEmpty(message = "Drink name cannot be left empty.")
    @Size(max = 50, message = "Drink name cannot be longer than 50 characters.")
    private String drinkName;

    @NotNull(message = "Base price cannot be left null.")
    @NotBlank(message = "Base price cannot be left blank.")
    @NotEmpty(message = "Base price cannot be left empty.")
    private double basePrice;

    private double price;
    
    private List<String> ingredientsList;

    protected CoffeeDto() {}

    public CoffeeDto(String drinkName, double basePrice, List<String> ingredientList) {
        this.drinkName = drinkName;
        this.basePrice = basePrice;
        this.price = calculateUpcharge(basePrice, size);
        this.ingredientsList = ingredientList;
    }

    public CoffeeDto(CoffeeDto source) {
        this.id = source.id;
        this.drinkName = source.drinkName;
        this.basePrice = source.basePrice;
        this.price = calculateUpcharge(basePrice, size);
        this.ingredientsList = source.ingredientsList;
    }

    protected CoffeeDto(int id, CoffeeSize size, String drinkName, double basePrice, List<String> ingredientList) {
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
}
