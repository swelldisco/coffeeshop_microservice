package com.java_coffee.user_service.user;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.java_coffee.user_service.exceptions.UserNotFoundException;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserServiceImpl implements UserService , UserDetailsService {

    @Autowired
    private UserRepository repo;
    @Autowired
    private UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        if (isEmail(username)) {
            user = new User(checkOptionalUserByEmail(username));
        } else {
            user = new User(checkOptionalUserByName(username));
        }

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmailAddress())
            .password(user.getPasswordHash())
            .roles(user.getRole().toString())
            .build();
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User savedUser = repo.save(new User(userDto.userName(), userDto.emailAddress(), userDto.password()));
        return mapper.mapToDto(savedUser);
    }

    @Override
    public UserDto getUserById(long userId) {
        return mapper.mapToDto(checkOptionalUserById(userId));
    }

    @Override
    public UserDto getUserByName(String userName) {
        return mapper.mapToDto(checkOptionalUserByName(userName));
    }

    @Override
    public UserDto getUserByEmail(String emailAddress) {
        return mapper.mapToDto(checkOptionalUserByEmail(emailAddress));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return repo.findAll().stream()
            .map(u -> mapper.mapToDto(u))
            .toList();
    }

    // right now this just silently fails to update username or email address if they're already in use.
    // this also does not touch the password or salt
    @Override
    public UserDto updateUser(long userId, UserDto userDto) {
        if (repo.existsByUserId(userDto.userId()) && userId == userDto.userId()) {
            User updatedUser = new User(checkOptionalUserById(userDto.userId()));
            if (!repo.existsByUserNameIgnoreCase(userDto.userName())) {
                updatedUser.setUserName(userDto.userName());
            }
            if (!repo.existsByEmailAddressIgnoreCase(userDto.emailAddress())) {
                updatedUser.setEmailAddress(userDto.emailAddress());
            }
            updatedUser.setFirstName(userDto.firstName());
            updatedUser.setLastName(userDto.lastName());
            updatedUser.setRole(userDto.role());
            updatedUser.setIsBanned(userDto.isBanned());
            updatedUser.setIsSuspended(userDto.isSuspended());
            updatedUser.setIsConfirmed(userDto.isConfirmed());
            return mapper.mapToDto(repo.save(updatedUser));
        } else {
            throw new UserNotFoundException("User", "ID", String.valueOf(userId));
        }
    }

    @Override
    public void deleteUserById(long userId) {
        if (repo.existsByUserId(userId)) {
            repo.deleteByUserId(userId);
        } else {
            throw new UserNotFoundException("User", "ID", String.valueOf(userId));
        }
    }

    // for comparing the password submitted on login attempt with the saved hash, allows either the user name or email address to be used as an account identifier.
    @Override
    public boolean verifyPassword(String password, String identifier) {
        if (isEmail(identifier)) {
            User user = checkOptionalUserByEmail(identifier);
            return user.verifyPassword(password);
        } else {
            User user = checkOptionalUserByName(identifier);
            return user.verifyPassword(password);
        }
    }

    @Override
    public void changePassword(String password, long userId) {
            User user = checkOptionalUserById(userId);
            user.setPasswordHash(password);
            repo.save(user);
    }

    // probably needed for resetting a password when a user cannot log in.  This checks if the identifier is an email address, if the identifier exists in the database, and returns an email address that a presumed email service can use to send a password reset link
    @Override
    public String resetPassword(String identifier) {
        if (isEmail(identifier)) {
            if (repo.existsByEmailAddressIgnoreCase(identifier)) {
                return identifier;
            } else {
                throw new UserNotFoundException("User", "Email Address", identifier);
            }
        } else {
            User user = checkOptionalUserByName(identifier);
            return user.getEmailAddress();
        }
    }

    // I am going to assume for now that the front end can use these to check if a user name or email address is already in use before allowing someone to submit it when creating accounts or changing these fields
    @Override
    public boolean checkUserName(String userName) {
        return repo.existsByUserNameIgnoreCase(userName);
    }

    @Override
    public boolean checkEmailAddress(String emailAddress) {
        return repo.existsByEmailAddressIgnoreCase(emailAddress);
    }

    // optional testing
    private User checkOptionalUserById(long userId) {
        return repo.findByUserId(userId)
            .orElseThrow(() -> new UserNotFoundException("User", "ID", String.valueOf(userId)));
    }

    private User checkOptionalUserByEmail(String emailAddress) {
        return repo.findByEmailAddressIgnoreCase(emailAddress)
            .orElseThrow(() -> new UserNotFoundException("User", "Email Address", emailAddress));
    }

    private User checkOptionalUserByName(String userName) {
        return repo.findByUserNameIgnoreCase(userName)
            .orElseThrow(() -> new UserNotFoundException("User", "User Name", userName));
    }

    // email regex matcher for testing if someone is logging in with an email address instead of username
    private boolean isEmail(String target) {
        // regex pattern from RFC 5322
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        Matcher emailMatcher = emailPattern.matcher(target);
        return emailMatcher.matches();
    }

    
}
