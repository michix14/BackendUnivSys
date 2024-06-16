package com.si2.parcial2.services;

import java.util.List;
import java.util.Optional;

import com.si2.parcial2.entities.User;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);

    User save(User user);

    boolean existsByUsername(String username);
}
