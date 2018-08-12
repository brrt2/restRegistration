package com.bartosz.pieczara.registration.controller;

import com.bartosz.pieczara.registration.model.User;
import com.bartosz.pieczara.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    private static final String USERNAME_EXISTS_MESSAGE = "This username already exists.";
    private static final String USERNAME_EXISTS_CODE = "username.exists";
    private static final String CREATE_ACCOUNT_VIEW = "createUser";
    private static final String ACCOUNT_CREATED_VIEW = "userCreated";

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String showMainPage(Model model) {

        User user = new User();
        model.addAttribute("user", user);
        return CREATE_ACCOUNT_VIEW;
    }

    @PostMapping(value = "/users")
    public String saveUser(@ModelAttribute @Valid User user, Errors errors) {

        if (userService.isUserAlreadyPresent(user)) {
            errors.rejectValue("username", USERNAME_EXISTS_CODE, USERNAME_EXISTS_MESSAGE);
        }

        if (errors.hasErrors()) {
            return CREATE_ACCOUNT_VIEW;
        }

        userService.saveUser(user);

        return "redirect:" + ACCOUNT_CREATED_VIEW;
    }

    @GetMapping(value = "/userCreated")
    public String showUserCreatedPage() {

        return ACCOUNT_CREATED_VIEW;
    }

}
