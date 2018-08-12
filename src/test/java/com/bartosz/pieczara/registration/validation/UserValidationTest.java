package com.bartosz.pieczara.registration.validation;

import com.bartosz.pieczara.registration.model.User;
import org.testng.annotations.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class UserValidationTest {

    private static final String CORRECT_PASSWORD = "January1";
    private static final String CORRECT_USERNAME = "bartek";

    private static ValidatorFactory factory;
    private static Validator validator;
    private User user;
    private Set<ConstraintViolation<User>> constraintViolations;

    @BeforeClass
    public static void setUpTestClass() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterClass
    public static void close() {
        factory.close();
    }

    @BeforeMethod
    public void setUpEachTest() {

        user = new User();

    }

    @DataProvider
    public Object[] provideIncorrectPasswords() {
        return new Object[]{
                "abc",
                "",
                "-",
                " ",
                "abc",
                "PasswordWithoutDigits",
                "ALL_UPPERCASE",
                "all-lowercase",
                ".",
                "'user'",
                "0",
                "12345678",
                "U+006D",
                "U+0020"

        };
    }

    @DataProvider
    public Object[] provideIncorrectUsernames() {
        return new Object[]{
                "@user",
                "username@#$",
                "-sample-",
                "Tom&Jerry",
                "John_Wayne",
                "sample-user",
                "yet/another/user",

        };
    }

    @DataProvider
    public Object[][] provideCorrectPasswordsAndUsernames() {
        return new Object[][]{
                {"bartek", "Administrator1"},
                {"sampleUser", "January1"},
                {"sampleUser2", "February2"},
                {"sampleUser3", "September9"},
                {"sampleUser4", "December12"},

        };
    }

    @DataProvider
    public Object[][] provideIncorrectPasswordsAndUsernames() {
        return new Object[][]{
                {"bartek@", "letmein"},
                {"sampleUser!", "password"},
                {"Bonnie&Clyde", "February"},
                {"JohnWayne12!!", "september9"},
                {"*sampleUser*", "december"},

        };
    }

    @Test(dataProvider = "provideIncorrectPasswords")
    public void shouldFindOneConstraintValidation_WhenPasswordIsIncorrect(String password) {

        // Arrange
        user.setUsername(CORRECT_USERNAME);
        user.setPassword(password);

        // Act
        constraintViolations = validator.validate(user);

        // Assert
        assertEquals(constraintViolations.size(), 1);
    }

    @Test(dataProvider = "provideIncorrectUsernames")
    public void shouldFindOneConstraintValidation_WhenIncorrectUsernameIsProvided(String username) {

        // Arrange
        user.setUsername(username);
        user.setPassword(CORRECT_PASSWORD);

        // Act
        constraintViolations = validator.validate(user);

        // Assert
        assertEquals(constraintViolations.size(), 1);
    }

    @Test(dataProvider = "provideIncorrectPasswordsAndUsernames")
    public void shouldFindTwoConstraintValidations_WhenIncorrectUsernameAndPasswordAreProvided(String username,
                                                                                               String password) {
        // Arrange
        user.setUsername(username);
        user.setPassword(password);

        // Act
        constraintViolations = validator.validate(user);

        // Assert
        assertEquals(constraintViolations.size(), 2);
    }

    @Test(dataProvider = "provideCorrectPasswordsAndUsernames")
    public void shouldFindNoConstraintValidations_WhenPasswordAndUsernameAreCorrect(String username, String password) {

        // Arrange
        user.setUsername(username);
        user.setPassword(password);

        // Act
        constraintViolations = validator.validate(user);

        // Assert
        assertEquals(constraintViolations.size(), 0);
    }

}