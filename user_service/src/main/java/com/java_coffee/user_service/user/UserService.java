package com.java_coffee.user_service.user;

import java.util.List;

public interface UserService {

    // crud stuff
    UserDto createUser(UserDto userDto);
    UserDto getUserById(long userId);
    UserDto getUserByName(String userName);
    UserDto getUserByEmail(String emailAddress);
    List<UserDto> getAllUsers();
    UserDto updateUser(long userId, UserDto userDto);
    void deleteUserById(long userId);

    // password security functionality
    boolean verifyPassword(String password, String identifier);
    void changePassword(String password, long userId);
    String resetPassword(String identifier);
    void resetSalt(long userId);

    // I think these might be useful to the front end for registration or updating accounts
    boolean checkUserName(String userName);
    boolean checkEmailAddress(String emailAddress);

}
