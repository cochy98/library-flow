package com.cosmo.app.services;

import java.util.List;
import java.util.Optional;

import com.cosmo.app.models.User;
import com.cosmo.app.repositories.UserRepository;

public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public void addUser(User user) {
        if (user.getName().isBlank() || user.getSurname().isBlank() || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("tutti i campi sono obbligatori.");
        }
        if (getUser(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("email già registrata.");
        }
        repository.save(user);
    }

    public Optional<User> getUser(String email) {
        return repository.findByEmail(email);
    }
}
