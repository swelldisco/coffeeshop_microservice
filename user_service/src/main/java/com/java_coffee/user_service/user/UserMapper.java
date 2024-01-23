package com.java_coffee.user_service.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    
    protected User mapToUser(UserDto source) {
        return new User(
            source.userId(),
            source.userName(),
            source.role(),
            source.emailAddress(),
            source.firstName(),
            source.lastName(),
            // password and salt are null
            source.joinDate(),
            source.isBanned(),
            source.isSuspended(),
            source.suspensionDate(),
            source.suspensionExpiration(),
            source.isConfirmed()
        );
    }

    protected UserDto mapToDto(User source) {
        return new UserDto(
            source.getUserId(),
            source.getUserName(),
            source.getRole(),
            source.getEmailAddress(),
            source.getFirstName(),
            source.getLastName(),
            "****",
            source.getJoinDate(),
            source.getIsBanned(),
            source.getIsSuspended(),
            source.getSuspensionDate(),
            source.getSuspensionExpiration(),
            source.getIsConfirmed()
        );
    }
    
}
