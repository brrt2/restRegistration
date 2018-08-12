package com.bartosz.pieczara.registration.service;

import com.bartosz.pieczara.registration.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    boolean isUserAlreadyPresent(User user);

    List<User> findAll();

    Optional<User> findById(long id);

    void deleteById(long id);

}
