package com.java_coffee.user_service.user.constants;

public enum Role {
    USER("User"), ADMIN("Administrator"), BARISTA("Barista");

    private String value;

    Role(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
