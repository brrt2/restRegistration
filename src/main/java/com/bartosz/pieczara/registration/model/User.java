package com.bartosz.pieczara.registration.model;

import com.bartosz.pieczara.registration.validation.ValidPassword;
import com.bartosz.pieczara.registration.validation.ValidUsername;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // @ValidUsername
    @NotNull
    @Size(min=5, message=" Username should be at least 5 characters long")
    @Pattern(regexp = "^[A-Za-z0-9]+$",message = "Only alphanumeric characters are allowed")
    private String username;

    // @ValidPassword
    @NotNull
    @Size(min=8, message=" Password should be at least 8 characters long")
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username, password);
    }
}
