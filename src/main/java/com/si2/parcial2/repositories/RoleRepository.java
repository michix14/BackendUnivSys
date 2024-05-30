package com.si2.parcial2.repositories;

import org.springframework.data.repository.CrudRepository;

import com.si2.parcial2.entities.Role;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
