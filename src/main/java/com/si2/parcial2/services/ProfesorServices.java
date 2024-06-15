package com.si2.parcial2.services;

import java.util.List;
import java.util.Optional;

import com.si2.parcial2.entities.User;
import com.si2.parcial2.entities.profesor;
import com.si2.parcial2.projection.MateriaInfo;

public interface ProfesorServices {

    List<profesor> findAll();

    Optional<profesor> findById(Long idProfesor);

    profesor save(profesor p);

    void deleteById(Long idProfesor);

    profesor registerProfesorWithUser(profesor profesor, User user);

    profesor findByUserUsername(String username);

    List<MateriaInfo> findMateriasByProfesorId(Long idProfesor);
}
