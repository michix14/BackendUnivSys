package com.si2.parcial2.services;

import java.util.List;
import java.util.Optional;

import com.si2.parcial2.entities.profesor;
public interface ProfesorServices {

    List<profesor> findAll();
    Optional<profesor> findById(Long idProfesor);
    profesor save(profesor p);
    void deleteById(Long idProfesor);
    boolean existsBySku(String sku);
    
}
