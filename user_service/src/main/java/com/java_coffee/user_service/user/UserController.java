package com.java_coffee.user_service.user;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.java_coffee.user_service.user.constants.Role;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api_v1/users")
@AllArgsConstructor
public class UserController {

    @Autowired
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

    // these need to be rethunk, because this seems to not be how to do this
    // http://127.0.0.1:8081/api_v1/users/isEmailInUse?email=123@gmail.com
    @GetMapping(value = "/isEmailInUse", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isEmailInUse(@RequestParam String email) {
        return new ResponseEntity<>(userService.checkEmailAddress(email), HttpStatus.OK);
    }

    // http://127.0.0.1:8081/api_v1/users/isNameInUse?name=BillyBobJimBob
    @GetMapping("/isNameInUse")
    public ResponseEntity<Boolean> isNameInUse(@RequestParam String name) {
        return new ResponseEntity<>(userService.checkUserName(name), HttpStatus.OK);
    }

    // http://127.0.0.1:8081/api_v1/users/resetPassword?identifier=Billy_Bob
    @GetMapping("/resetPassword")
    public ResponseEntity<HttpStatus> resetPassword(@RequestParam String identifier) {
        userService.resetPassword(identifier);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    // http://127.0.0.1:8081/api_v1/users/loadTestData
    @GetMapping("/loadTestData")
    public ResponseEntity<HttpStatus> loadTestData() {
        List<UserDto> testUserList = Arrays.asList(
            new UserDto(0L, "Billy_Bob", Role.USER, "B-B@gmail.com", null, null, "wieufgdjwat23785", null, false, false, null, null, false),
            new UserDto(0L ,"Jimbo", Role.USER, "JimmyMcBob@mail.ru", null, null, "uwoiagksdjbx", null, false, false, null, null, false),
            new UserDto(0L, "Bouregard_Bubba", Role.USER, "OtherB-B@gmail.com", null, null, "weioufgksdjhx", null, false, false, null, null, false),
            new UserDto(0L, "Cleeeeeeeeetus", Role.USER, "DonCLEETS@mail.ru", null, null, "wafegdsjhu", null, false, false, null, null, false),
            new UserDto(0L, "Junior", Role.USER, "JethroJr@yahoo.com", null, null, "iweluafkjbeiugd",null, false, false, null, null, false)
        );

        for (UserDto dto : testUserList) {
            userService.createUser(dto);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }    
}
