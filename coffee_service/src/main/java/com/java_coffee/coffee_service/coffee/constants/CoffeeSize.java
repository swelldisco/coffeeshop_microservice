package com.java_coffee.coffee_service.coffee.constants;

public enum CoffeeSize {
    SHORT("short"), TALL("Tall"), GRANDE("Grande"), VENTI("Venti");

    private String value;

    CoffeeSize(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
