package com.si2.parcial2.services;

import java.util.List;
import java.util.Optional;

import com.si2.parcial2.entities.Modulo;



public interface ModuloServices {
    
    List<Modulo> findAll();
    Optional<Modulo> findById(Long id);
    Modulo save(Modulo m);
    void deleteById(Long id);

}
