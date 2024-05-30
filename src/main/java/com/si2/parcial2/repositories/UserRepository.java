package com.si2.parcial2.repositories;

import org.springframework.data.repository.CrudRepository;

import com.si2.parcial2.entities.User;

import java.util.Optional;



public interface UserRepository extends CrudRepository<User, Long>{
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

}
