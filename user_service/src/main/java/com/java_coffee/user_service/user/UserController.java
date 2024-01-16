package com.java_coffee.user_service.user;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api_v1/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    // http://127.0.0.1:8081/api_v1/users/create
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    // http://127.0.0.1:8081/api_v1/users/id?userId=1
    @GetMapping("/id")
    public ResponseEntity<UserDto> getUserById(@RequestParam long userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    // http://127.0.0.1:8081/api_v1/users/userName?name=Billy_Bob
    @GetMapping("/userName")
    public ResponseEntity<UserDto> getUserByUserName(@RequestParam String name) {
        return new ResponseEntity<>(userService.getUserByName(name), HttpStatus.OK);
    }

    // http://127.0.0.1:8081/api_v1/users/email?emailAddress=123@gmail.com
    @GetMapping("/email")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String emailAddress) {
        return new ResponseEntity<>(userService.getUserByEmail(emailAddress), HttpStatus.OK);
    }

    // http://127.0.0.1:8081/api_v1/users/all
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    // http://127.0.0.1:8081/api_v1/users/1
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable long userId, @RequestBody @Valid UserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(userId, userDto), HttpStatus.ACCEPTED);
    }

    // http://127.0.0.1:8081/api_v1/users/1
    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable long userId) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // http://127.0.0.1:8081/api_v1/isEmailInUse?email=123@gmail.com
    @GetMapping("/isEmailInUse")
    public ResponseEntity<Boolean> isEmailInUse(@RequestParam String email) {
        return new ResponseEntity<>(userService.checkEmailAddress(email), HttpStatus.OK);
    }

    // http://127.0.0.1:8081/api_v1/isNameInUse?name=BillyBobJimBob
    @GetMapping("/isNameInUse")
    public ResponseEntity<Boolean> isNameInUse(@RequestParam String name) {
        return new ResponseEntity<>(userService.checkUserName(name), HttpStatus.OK);
    }

    // http://127.0.0.1:8081/api_v1/resetPassword
    @PutMapping("/resetPassword")
    public ResponseEntity<HttpStatus> resetPassword(@RequestBody String identifier) {
        userService.resetPassword(identifier);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    // http://127.0.0.1:8081/api_v1/loadTestData
    @PostMapping("/loadTestData")
    public ResponseEntity<HttpStatus> loadTestData() {
        List<UserDto> testUserList = Arrays.asList(
            new UserDto("Billy_Bob", "B-B@gmail.com", "wieufgdjwat23785"),
            new UserDto("Jimbo", "JimmyMcBob@mail.ru", "uwoiagksdjbx"),
            new UserDto("Bouregard_Bubba", "OtherB-B@gmail.com", "weioufgksdjhx"),
            new UserDto("Cleeeeeeeeetus", "DonCLEETS@mail.ru", "wafegdsjhu"),
            new UserDto("Junior", "JethroJr@yahoo.com", "iweluafkjbeiugd")
        );

        for (UserDto dto : testUserList) {
            userService.createUser(dto);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }    
}
