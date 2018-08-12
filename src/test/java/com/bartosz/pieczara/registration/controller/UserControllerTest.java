package com.bartosz.pieczara.registration.controller;

import com.bartosz.pieczara.registration.repository.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

    private static final String VALID_PASSWORD = "Administrator123";
    private static final String INVALID_PASSWORD = "letmein";
    private static final String VALID_USERNAME = "bartek";
    private static final String INVALID_USERNAME = "%invalidUser!";
    private static final String USERNAME_ARGUMENT = "username";
    private static final String PASSWORD_ARGUMENT = "password";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldDetectTwoErrors_WhenUsernameAndPasswordAreIncorrect() throws Exception {

        //Act
        this.mockMvc
                .perform(
                        post("/users")
                                .param(USERNAME_ARGUMENT, INVALID_USERNAME)
                                .param(PASSWORD_ARGUMENT, INVALID_PASSWORD)

                )

                //Assert
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(2))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDetectOneError_WhenPasswordIsIncorrect() throws Exception {

        //Act
        this.mockMvc
                .perform(
                        post("/users")
                                .param(USERNAME_ARGUMENT, VALID_USERNAME)
                                .param(PASSWORD_ARGUMENT, INVALID_PASSWORD)

                )

                //Assert
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(1))
                .andExpect(status().isOk());
    }


    @Test
    public void shouldDetectOneError_WhenUsernameIsIncorrect() throws Exception {

        //Act
        this.mockMvc
                .perform(
                        post("/users")
                                .param(USERNAME_ARGUMENT, INVALID_USERNAME)
                                .param(PASSWORD_ARGUMENT, VALID_PASSWORD)

                )

                //Assert
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(1))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDetectNoErrors_WhenInputIsCorrect() throws Exception {

        //Act
        this.mockMvc
                .perform(
                        post("/users")
                                .param(USERNAME_ARGUMENT, VALID_USERNAME)
                                .param(PASSWORD_ARGUMENT, VALID_PASSWORD)

                )

                //Assert
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void shouldDetectError_WhenTryingToPersistTheSameUserTwice() throws Exception {

        //Act
        this.mockMvc
                .perform(
                        post("/users")
                                .param(USERNAME_ARGUMENT, VALID_USERNAME)
                                .param(PASSWORD_ARGUMENT, VALID_PASSWORD)

                );

        this.mockMvc
                .perform(
                        post("/users")
                                .param(USERNAME_ARGUMENT, VALID_USERNAME)
                                .param(PASSWORD_ARGUMENT, VALID_PASSWORD)

                )

                //Assert
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(1))
                .andExpect(status().isOk());

    }


}
