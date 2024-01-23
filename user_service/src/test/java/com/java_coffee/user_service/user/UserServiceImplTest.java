package com.java_coffee.user_service.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.java_coffee.user_service.exceptions.UserNotFoundException;
import com.java_coffee.user_service.user.constants.Role;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceImplTest {

    private UserMapper mapper = new UserMapper();

    LocalDateTime date1 = LocalDateTime.now().minusYears(3);

    @Mock(name = "database")
    private UserRepository repo;

    @InjectMocks
    private UserServiceImpl service = new UserServiceImpl(repo, mapper);

    List<User> testUserList;
    UserDto testUserDto;
    User testUser;
    User user1;
    User user2;
    User user3;
    User user4;
    User user5;

   @BeforeEach
   void setUp() {
        testUserDto = new UserDto(1L, "Billy_Bob", Role.USER, "B-B@gmail.com", null, null, "wieufgdjwat23785", null, false, false, null, null, false);

        testUser = new User(mapper.mapToUser(testUserDto));

        User user1 = new User(0, "Billy_Bob", Role.USER, "B-B@gmail.com", null, null, date1, false, false, null, null, true);
        user1.resetSalt();
        user1.setPasswordHash("ieaugkfs236432");

        User user2 = new User(1, "Jimbo", Role.USER, "Jimbo@mail.ru", null, null, date1, false, false, null, null, true);
        user2.resetSalt();
        user2.setPasswordHash("euifhdjgfejgfdjag");

        User user3 = new User(2, "Cleets", Role.USER, "Anemail@email.com", null, null, date1, false, false, null, null, true);
        user3.resetSalt();
        user3.setPasswordHash("gfdshjwuegjsd");

        User user4 = new User(3, "Jethro", Role.USER, "jgimpy@UofT.edu", null, null, date1, false, false, null, null, true);
        user4.resetSalt();
        user4.setPasswordHash("wieufd83ye");

        User user5 = new User(4, "Bubba", Role.USER, "bubs@mail.ru", null, null, date1, false, false, null, null, true);
        user5.resetSalt();
        user5.setPasswordHash("uwqeihkjd37842");

        testUserList = new ArrayList<>();
        testUserList.add(user1);
        testUserList.add(user2);
        testUserList.add(user3);
        testUserList.add(user4);
        testUserList.add(user5);

        
   }

   @AfterEach
   void tearDown() {
        testUserList = null;
        testUserDto = null;
        testUser = null;
        user1 = null;
        user2 = null;
        user3 = null;
        user4 = null;
        user5 = null;
   }

    @Test
    void testCreateUser() {
        // given
        Assertions.assertNotNull(testUserDto);
        Assertions.assertNotNull(testUser);
        List<User> emptyList = Arrays.asList(testUser);
        Assertions.assertEquals(1, emptyList.size());
        long longId = testUser.getUserId();
        Assertions.assertEquals(longId, testUserDto.userId());
        User anotherUser = new User(emptyList.get(0));
        Assertions.assertNotNull(anotherUser);

        //when
        when(repo.save(any(User.class))).thenReturn(testUser);
        Assertions.assertNotNull(repo.save(testUser));
        when(repo.findAll()).thenReturn(emptyList);
        when(repo.findByUserId(longId)).thenReturn(Optional.of(emptyList.get((int)longId - 1)));
        
        // for whatever reason, the mapper is somehow failing during the save?
        // somehow this may have fixed the error:
        //      added equals and hashcode to both DTO and Entity
        //      tried this as the stub:
        //      when(repo.save(any(User.class))).thenReturn(testUser); (matchers.any)
        //      instead of:
        //      when(repo.save(testUser)).thenReturn(testUser);
        // seems like it just needs matchers.any in order for it to work with my entity
        UserDto checkSave = service.createUser(testUserDto);
        UserDto userDto = service.getUserById(longId);

        // then
        Assertions.assertNotNull(userDto.userId());
        Assertions.assertEquals(checkSave, userDto);
        Assertions.assertEquals(testUserDto.userName(), userDto.userName());
        Assertions.assertEquals(userDto.password(), "****");
    }

    @Test
    void testCheckEmailAddress() {
        // given
        Assertions.assertNotNull(testUserList);
        String goodEmail = "B-B@gmail.com";
        String badEmail = "Not_Used@gmail.com";

        // when
        when(repo.existsByEmailAddressIgnoreCase(goodEmail)).thenReturn(testUserList.stream()
            .anyMatch(u -> u.getEmailAddress().equalsIgnoreCase(goodEmail)));
        when(repo.existsByEmailAddressIgnoreCase(badEmail)).thenReturn(testUserList.stream()
            .anyMatch(u -> u.getEmailAddress().equalsIgnoreCase(badEmail)));

        boolean trueTest = service.checkEmailAddress(goodEmail);
        boolean falseTest = service.checkEmailAddress(badEmail);

        // then
        Assertions.assertNotEquals(goodEmail, badEmail);
        Assertions.assertFalse(falseTest);
        Assertions.assertTrue(trueTest);
    }

    @Test
    void testCheckUserName() {
        // given
        Assertions.assertNotNull(testUserList);
        String goodUserName = "Jimbo";
        String badUserName = "Unused_Name";

        // when
        when(repo.existsByUserNameIgnoreCase(goodUserName)).thenReturn(testUserList.stream()
            .anyMatch(u -> u.getUserName().equalsIgnoreCase(goodUserName)));
        when(repo.existsByUserNameIgnoreCase(badUserName)).thenReturn(testUserList.stream()
            .anyMatch(u -> u.getUserName().equalsIgnoreCase(badUserName)));

        boolean trueTest = service.checkUserName(goodUserName);
        boolean falseTest = service.checkUserName(badUserName);

        // then
        Assertions.assertNotEquals(goodUserName, badUserName);
        Assertions.assertFalse(falseTest);
        Assertions.assertTrue(trueTest);
    }

    @Test
    void testGetUserById() {
        // given
        Assertions.assertNotNull(testUserList);
        User checkUser = testUserList.get(3);
        long testUserId1 = checkUser.getUserId();
        long testUserId2 = 75;

        // when
        when(repo.findByUserId(testUserId1)).thenReturn(Optional.of(testUserList.get((int)testUserId1)));
        when(repo.findByUserId(testUserId2)).thenThrow(UserNotFoundException.class);
        
        // then
        Assertions.assertThrowsExactly(UserNotFoundException.class, () -> repo.findByUserId(testUserId2));
        Assertions.assertNotNull(service.getUserById(testUserId1));
        
        UserDto checkUser1 = service.getUserById(testUserId1);
        Assertions.assertEquals(checkUser.getUserId(), checkUser1.userId());
        Assertions.assertEquals(checkUser.getUserName(), checkUser1.userName());
    }

    @Test
    void testGetUserByName() {
        // given
        Assertions.assertNotNull(testUserList);
        User checkUser = testUserList.get(3);
        int checkId = (int)checkUser.getUserId();
        String goodName = checkUser.getUserName();
        String badName = "welaihsFJK";
        
        // when
        when(repo.findByUserNameIgnoreCase(goodName)).thenReturn(Optional.of(testUserList.get(checkId)));
        when(repo.findByUserNameIgnoreCase(badName)).thenThrow(UserNotFoundException.class);
        UserDto checkDto = service.getUserByName(goodName);

        // then
        Assertions.assertNotEquals(goodName, badName);
        Assertions.assertThrowsExactly(UserNotFoundException.class, () -> service.getUserByName(badName));
        Assertions.assertEquals(checkDto.userId(), checkUser.getUserId());
        Assertions.assertEquals(checkDto.userName(), checkUser.getUserName());
        Assertions.assertEquals(checkDto.emailAddress(), checkUser.getEmailAddress());
    }

    @Test
    void testGetUserByEmail() {
        //given
        Assertions.assertNotNull(testUserList);
        int checkId = 3;
        User checkUser = testUserList.get(checkId);
        Assertions.assertEquals(checkId, (int)checkUser.getUserId());
        String goodEmail = checkUser.getEmailAddress();
        String badEmail = "Unused_email_address@gmail.com";

        // when
        when(repo.findByEmailAddressIgnoreCase(goodEmail)).thenReturn(Optional.of(testUserList.get(checkId)));
        when(repo.findByEmailAddressIgnoreCase(badEmail)).thenThrow(UserNotFoundException.class);
        UserDto checkDto = service.getUserByEmail(goodEmail);

        // then
        Assertions.assertNotEquals(goodEmail, badEmail);
        Assertions.assertThrowsExactly(UserNotFoundException.class, () -> service.getUserByEmail(badEmail));
        Assertions.assertEquals(checkDto.userId(), checkUser.getUserId());
        Assertions.assertEquals(checkDto.userName(), checkUser.getUserName());
        Assertions.assertEquals(checkDto.emailAddress(), checkUser.getEmailAddress());
    }

    @Test
    void testGetAllUsers() {
        // given
        Assertions.assertNotNull(testUserList);
        int testIndex = 3;
        User checkUser = testUserList.get(testIndex);

        // when
        when(repo.findAll()).thenReturn(testUserList);
        List<UserDto> checkList = service.getAllUsers();
        UserDto checkDto = checkList.get(testIndex);

        // then
        Assertions.assertNotNull(checkList);
        Assertions.assertNotNull(checkDto);
        Assertions.assertNotEquals(15, checkDto.userId());
        Assertions.assertEquals(checkUser.getUserId(), checkDto.userId());
        Assertions.assertEquals(checkUser.getEmailAddress(), checkDto.emailAddress());
    }

    @Test
    void testUpdateUser() {
        // given
        Assertions.assertNotNull(testUserList);
        int testIndex = 3;
        User checkUser = testUserList.get(testIndex);
        Assertions.assertNotNull(checkUser);
        Assertions.assertEquals(testIndex, checkUser.getUserId());
        Assertions.assertNull(checkUser.getFirstName());
        String newFirstName = "john";
        String oldUserName = checkUser.getUserName();
        String newUserName = "a new user name";
        checkUser.setFirstName(newFirstName);
        checkUser.setUserName(newUserName);
        UserDto dto = mapper.mapToDto(checkUser);

        // when
        when(repo.existsByUserId(testIndex)).thenReturn(testUserList.stream()
            .anyMatch(u -> u.getUserId() == (long) testIndex));
        when(repo.save(any(User.class))).thenReturn(testUserList.set(testIndex, checkUser));
        when(repo.findByUserId((testIndex))).thenReturn(Optional.of(testUserList.get(testIndex)));
        service.updateUser(testIndex, dto);
        testUserList.set(testIndex, checkUser);
        UserDto checkDto = service.getUserById(testIndex);

        // then
        Assertions.assertNotNull(repo.save(checkUser));
        Assertions.assertNotNull(checkDto);
        Assertions.assertNotEquals(oldUserName, newUserName);
        Assertions.assertNotNull(checkDto.firstName());
        Assertions.assertEquals(newFirstName, checkDto.firstName());
        Assertions.assertNotEquals(oldUserName, checkDto.userName());
        Assertions.assertEquals(newUserName, checkDto.userName());

    }

    @Test
    void testDeleteUserById() {
        // given
        Assertions.assertNotNull(testUserList);
        int testIndex = 3;

        // when
        when(repo.existsByUserId(testIndex)).thenReturn(true);
        doNothing().when(repo).deleteByUserId(testIndex);
        service.deleteUserById(testIndex);

        // then
        verify(repo, times(1)).deleteByUserId(testIndex);
    }
    
    @Test
    void testChangePassword() {
        // given
        Assertions.assertNotNull(testUserList);
        int testIndex = 3;
        User testUser = new User(testUserList.get(testIndex));
        Assertions.assertNotNull(testUser);
        String currentPasswordHash = testUser.getPasswordHash();
        Assertions.assertNotNull(currentPasswordHash);
        Assertions.assertEquals(currentPasswordHash, testUser.getPasswordHash());
        String newPassword = "qeag2hw35yw7etas";
        User testUser2 = new User(testUserList.get(testIndex));
        Assertions.assertEquals(testUser.getPasswordHash(), testUser2.getPasswordHash());
        testUser.setPasswordHash(newPassword);
        Assertions.assertNotEquals(currentPasswordHash, testUser.getPasswordHash());
        Assertions.assertNotEquals(testUser.getPasswordHash(), testUser2.getPasswordHash());
        
        // when
        when(repo.findByUserId(testIndex)).thenReturn(Optional.of(new User(testUserList.get(testIndex)))); 
        when(repo.save(any(User.class))).thenReturn(testUser);
        service.changePassword(newPassword, testIndex);

        // then
        verify(repo, times(1)).findByUserId(testIndex);
        verify(repo, times(1)).save(any(User.class));
        
    }
    

    // as of testing, this only checks if the input is a email address or not, then whether or not it is a username or email address that is currently in use and return the email address for that account
    @Test
    void testResetPassword() {
        // given
        Assertions.assertNotNull(testUserList);
        String anActualEmail = testUserList.get(0).getEmailAddress();
        Assertions.assertNotNull(anActualEmail);
        String anActualUserName = testUserList.get(3).getUserName();
        String actualUserNameEmail = testUserList.get(3).getEmailAddress();
        Assertions.assertNotNull(anActualUserName);
        Assertions.assertNotNull(actualUserNameEmail);
        String notAnEmail = "bad@address@forTes.t";
        String unusedEmail = "UnusedEmailAddress@mail.com";
        String unusedUserName = "unused_name";
        
        // when
        when(repo.existsByEmailAddressIgnoreCase(anActualEmail)).thenReturn(true);
        when(repo.existsByEmailAddressIgnoreCase(unusedEmail)).thenReturn(false);
        when(repo.existsByEmailAddressIgnoreCase(notAnEmail)).thenReturn(testUserList.stream()
            .anyMatch(u -> u.getEmailAddress().equalsIgnoreCase(notAnEmail)));
        when(repo.findByUserNameIgnoreCase((anActualUserName))).thenReturn(Optional.of(testUserList.get(3)));

        String emailTestOne = service.resetPassword(anActualEmail);
        String userNameTestOne = service.resetPassword(anActualUserName);

        // then
        Assertions.assertThrowsExactly(UserNotFoundException.class, () -> service.resetPassword(unusedEmail));
        Assertions.assertThrowsExactly(UserNotFoundException.class, () -> service.resetPassword(unusedUserName));
        Assertions.assertThrowsExactly(UserNotFoundException.class, () -> service.resetPassword(notAnEmail));
        Assertions.assertNotNull(emailTestOne);
        Assertions.assertEquals(anActualEmail, emailTestOne);
        Assertions.assertNotNull(userNameTestOne);
        Assertions.assertEquals(actualUserNameEmail, userNameTestOne);
    }

    
    @Test
    void testVerifyPassword() {
        // given
        Assertions.assertNotNull(testUserList);
        String anActualEmail = testUserList.get(0).getEmailAddress();
        Assertions.assertNotNull(anActualEmail);
        String anActualUserName = testUserList.get(3).getUserName();
        Assertions.assertNotNull(anActualUserName);
        Assertions.assertNotEquals(anActualEmail, anActualUserName);
        String unusedEmail = "unusedEmail@mail.com";
        String unusedUserName = "unused_user_name";
        String wrongPassword = "a wrong password";
        String realPassword1 = "ieaugkfs236432";
        String realPassword2 = "wieufd83ye";

        // when
        when(repo.findByEmailAddressIgnoreCase((anActualEmail))).thenReturn(Optional.of(testUserList.get(0)));
        when(repo.findByUserNameIgnoreCase((anActualUserName))).thenReturn(Optional.of(testUserList.get(3)));
        boolean boolFalse1 = service.verifyPassword(wrongPassword, anActualEmail);
        boolean boolFalse2 = service.verifyPassword(wrongPassword, anActualUserName);
        boolean boolFalse3 = service.verifyPassword(realPassword1, anActualUserName);
        boolean boolFalse4 = service.verifyPassword(realPassword2, anActualEmail);
        boolean boolTrue1 = service.verifyPassword(realPassword1, anActualEmail);
        boolean boolTrue2 = service.verifyPassword(realPassword2, anActualUserName);
        
        // then
        Assertions.assertThrowsExactly(UserNotFoundException.class, () -> service.verifyPassword(wrongPassword, unusedEmail));
        Assertions.assertThrowsExactly(UserNotFoundException.class, () -> service.verifyPassword(wrongPassword, unusedUserName));
        Assertions.assertFalse(boolFalse1);
        Assertions.assertFalse(boolFalse2);
        Assertions.assertFalse(boolFalse3);
        Assertions.assertFalse(boolFalse4);
        Assertions.assertTrue(boolTrue1);
        Assertions.assertTrue(boolTrue2);
    }

    @Test
    void testResetSalt() {
        // given
        Assertions.assertNotNull(testUserList);
        int testIndex = 1;
        User checkUser = new User(testUserList.get(testIndex));
        String resetPassToThisJustToBeSure = "reset_the_pass_word124";
        String checkEmail = checkUser.getEmailAddress();
        String checkUserName = checkUser.getUserName();

        // when
        when(repo.findByUserId(testIndex)).thenReturn(Optional.of(testUserList.get(testIndex)));
        when(repo.findByEmailAddressIgnoreCase(checkEmail)).thenReturn(Optional.of(testUserList.get(testIndex)));
        when(repo.findByUserNameIgnoreCase(checkUserName)).thenReturn(Optional.of(testUserList.get(testIndex)));
        when(repo.save(any(User.class))).thenReturn(checkUser);
        service.resetSalt(testIndex);
        
        // then
        // asserting the calls twice because you need to change the password after resetting the salt
        verify(repo, times(1)).findByUserId(testIndex);
        verify(repo, times(1)).save(any(User.class));

        // password verification should fail after resetting salt and before the password has been reset with the new salt
        Assertions.assertFalse(service.verifyPassword(resetPassToThisJustToBeSure, checkUserName));
        // once password is changed following a salt reset, verification should once again work
        service.changePassword(resetPassToThisJustToBeSure, testIndex);
        Assertions.assertTrue(service.verifyPassword(resetPassToThisJustToBeSure, checkUserName));
        
    }


}
