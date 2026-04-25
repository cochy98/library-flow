package com.cosmo.app.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cosmo.app.models.User;

public class InMemoryUserRepository implements UserRepository {
    private final List<User> users = new ArrayList<>();

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(users);
    }
}
