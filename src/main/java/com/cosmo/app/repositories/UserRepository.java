package com.cosmo.app.repositories;

import java.util.List;
import java.util.Optional;

import com.cosmo.app.models.User;

public interface UserRepository {
    void save(User user);

    Optional<User> findByEmail(String email);

    List<User> findAll();
}
