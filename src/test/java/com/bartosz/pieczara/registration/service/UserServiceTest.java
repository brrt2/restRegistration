package com.bartosz.pieczara.registration.service;

import com.bartosz.pieczara.registration.model.User;
import com.bartosz.pieczara.registration.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceTest {

    private static final String USERNAME = "bartek";
    private static final String PASSWORD = "January1";

    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void shouldReturnTrue_WhenTheGivenUserHasAlreadyBeenSaved() {

        //Arrange
        User user = new User(USERNAME, PASSWORD);

        // Act
        userService.saveUser(user);

        // Assert
        assertTrue(userService.isUserAlreadyPresent(user));
    }

    @Test
    public void shouldReturnFalse_WhenTheGivenUserHasNotBeenSavedBefore() {

        //Arrange
        User user = new User(USERNAME, PASSWORD);

        // Assert
        assertFalse(userService.isUserAlreadyPresent(user));
    }
}