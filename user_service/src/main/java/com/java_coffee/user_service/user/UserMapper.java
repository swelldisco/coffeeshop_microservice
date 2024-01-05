package com.java_coffee.user_service.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    
    protected User mapToUser(UserDto source) {
        return new User(
            source.getUserId(),
            source.getUserName(),
            source.getUserType(),
            source.getEmailAddress(),
            source.getFirstName(),
            source.getLastName(),
            // password and salt are null
            source.getJoinDate(),
            source.getIsBanned(),
            source.getIsSuspended(),
            source.getSuspensionDate(),
            source.getSuspensionExpiration(),
            source.getIsConfirmed()
        );
    }

    protected UserDto mapToDto(User source) {
        return new UserDto(
            source.getUserId(),
            source.getUserName(),
            source.getUserType(),
            source.getEmailAddress(),
            source.getFirstName(),
            source.getLastName(),
            
            source.getJoinDate(),
            source.getIsBanned(),
            source.getIsSuspended(),
            source.getSuspensionDate(),
            source.getSuspensionExpiration(),
            source.getIsConfirmed()
        );
    }
    
}
