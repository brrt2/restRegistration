package com.bartosz.pieczara.registration.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bartosz.pieczara.registration.exception.UserAlreadyPresent;
import com.bartosz.pieczara.registration.exception.UserNotFoundException;
import com.bartosz.pieczara.registration.model.User;
import com.bartosz.pieczara.registration.service.UserService;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class RestUserController {

    private UserService userService;

    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> retrieveAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/accounts/{id}")
    public Resource<User> retrieveUser(@PathVariable long id) {
        Optional<User> user = userService.findById(id);

        if (!user.isPresent())
            throw new UserNotFoundException("id-" + id);

        Resource<User> resource = new Resource<>(user.get());

        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @DeleteMapping("/accounts/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteById(id);
    }

    @PostMapping("/accounts")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {

        if (userService.isUserAlreadyPresent(user)) {
            throw new UserAlreadyPresent("User with this id is already present in the database !" + user.getId());
        }

        User savedUser = userService.saveUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody User user, @PathVariable long id) {

        Optional<User> userOptional = userService.findById(id);

        if (!userOptional.isPresent())
            throw new UserNotFoundException("id-" + id);

        user.setId(id);

        userService.saveUser(user);

        return ResponseEntity.noContent().build();
    }
}