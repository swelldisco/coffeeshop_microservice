package com.java_coffee.user_service.user.constants;

public enum UserType {
    USER("User"), ADMIN("Administrator"), GLOBAL_ADMIN("Global Administrator");

    private String value;

    UserType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
