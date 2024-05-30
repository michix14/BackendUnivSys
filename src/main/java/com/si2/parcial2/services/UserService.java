package com.si2.parcial2.services;

import java.util.List;

import com.si2.parcial2.entities.User;

public interface UserService {
    List<User> findAll();

    User save(User user);

    boolean existsByUsername(String username);
}
