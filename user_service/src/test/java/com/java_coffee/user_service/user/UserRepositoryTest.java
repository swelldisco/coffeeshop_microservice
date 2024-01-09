package com.java_coffee.user_service.user;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository repo;
    User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("Billy_Bob", "B-B@gmail.com", "ieaugkfs236432");
    }

    @AfterEach 
    void tearDown() {
        testUser = null;
    }

    @Test
    void testSave() {
        User user = repo.save(testUser);

        Assertions.assertNotNull(user.getUserId());
        Assertions.assertEquals(testUser.getUserName(), user.getUserName());
        Assertions.assertEquals(testUser.getEmailAddress(), user.getEmailAddress());
        Assertions.assertEquals(testUser.getPasswordHash(), user.getPasswordHash());
        Assertions.assertEquals(testUser.getJoinDate(), user.getJoinDate());
        Assertions.assertTrue(user.verifyPassword("ieaugkfs236432"));
    }

    @Test
    void testGetByUserId() {
        repo.save(testUser);

        Optional<User> optionalUser = repo.findByUserId(1);

        Assertions.assertNotNull(optionalUser.get().getUserName());
        Assertions.assertEquals(testUser.getUserName(), optionalUser.get().getUserName());
    }

    @Test
    void testExistsByUserId() {
        repo.save(testUser);

        Assertions.assertNotNull(repo.existsByUserId(1));
        Assertions.assertTrue(repo.existsByUserId(1));
        Assertions.assertFalse(repo.existsByUserId(359));
    }

    @Test
    void testExistsByUserNameIgnoreCase() {
        repo.save(testUser);
        String userName = testUser.getUserName();

        Assertions.assertNotNull(repo.existsByUserNameIgnoreCase(userName));
        Assertions.assertTrue(repo.existsByUserNameIgnoreCase(userName));
        Assertions.assertFalse(repo.existsByUserNameIgnoreCase("Another User Name"));
    }

    @Test
    void testExistsByEmailAddressIgnoreCase() {
        repo.save(testUser);
        String emailAddress = testUser.getEmailAddress();

        Assertions.assertNotNull(repo.existsByEmailAddressIgnoreCase(emailAddress));
        Assertions.assertTrue(repo.existsByEmailAddressIgnoreCase(emailAddress));
        Assertions.assertFalse(repo.existsByEmailAddressIgnoreCase("A different EMail Address"));
    }

    @Test
    void testFindByUserNameIgnoreCase() {
        repo.save(testUser);
        String userName = testUser.getUserName();

        Optional<User> user = repo.findByUserNameIgnoreCase(userName);

        Assertions.assertNotNull(user.get().getUserId());
        Assertions.assertEquals(userName, user.get().getUserName());
    }

    @Test
    void testFindByEmailAddressIgnoreCase() {
        repo.save(testUser);
        String emailAddress = testUser.getEmailAddress();

        Optional<User> user = repo.findByEmailAddressIgnoreCase(emailAddress);

        Assertions.assertNotNull(user.get().getUserId());
        Assertions.assertEquals(emailAddress, user.get().getEmailAddress());
    }

    @Test
    void testFindAll() {
        repo.save(testUser);
        User testUser2 = new User("Jim_Bob", "J_B@gmail.com", "kafgdkghaf325");
        repo.save(testUser2);

        List<User> userList = repo.findAll();
        User testUser3 = new User(userList.get(1));

        Assertions.assertNotNull(userList);
        Assertions.assertEquals(2, userList.size());
        Assertions.assertEquals(testUser3.getUserName(), testUser2.getUserName());
    }

    @Test
    void testDeleteById() {
        repo.save(testUser);
        Assertions.assertTrue(repo.existsByUserId(1));

        repo.deleteById(1);
        Assertions.assertFalse(repo.existsByUserId(1));
    }

}
