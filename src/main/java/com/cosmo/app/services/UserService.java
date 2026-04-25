package com.cosmo.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cosmo.app.models.User;

public class UserService {
    private List<User> users = new ArrayList<>();

    public List<User> getAllUsers() {
        return List.copyOf(users);
    }

    public void addUser(User user) {
        if (user.getName().isBlank() || user.getSurname().isBlank() || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("tutti i campi sono obbligatori.");
        }
        if (getUser(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("email già registrata.");
        }
        users.add(user);
    }

    public Optional<User> getUser(String email) {
        return users.stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst();
    }

}
