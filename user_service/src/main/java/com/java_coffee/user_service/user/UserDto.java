package com.java_coffee.user_service.user;

import java.time.LocalDateTime;

import com.java_coffee.user_service.user.constants.Role;

public record UserDto(long userId, String userName, Role role, String emailAddress, String firstName, String lastName, String password, LocalDateTime joinDate, boolean isBanned, boolean isSuspended, LocalDateTime suspensionDate, LocalDateTime suspensionExpiration, boolean isConfirmed) {}
